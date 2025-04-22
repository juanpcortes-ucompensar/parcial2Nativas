package com.example.multinativeapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.multinativeapp.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AnalisisFragment : Fragment() {

    private lateinit var etPeso: EditText
    private lateinit var spinnerNivelCondicion: Spinner
    private lateinit var etTiempoDisponible: EditText
    private lateinit var spinnerObjetivo: Spinner
    private lateinit var btnCalcularRutina: Button
    private lateinit var btnGuardarResultados: Button
    private lateinit var tvCaloriasQuemadas: TextView
    private lateinit var tvDistribucionEjercicios: TextView
    private lateinit var tvIntensidadRecomendada: TextView
    private lateinit var sharedPreferences: SharedPreferences

    private val nivelesCondicion = arrayOf("Principiante", "Intermedio", "Avanzado")
    private val objetivos = arrayOf("Pérdida de peso", "Tonificación", "Fuerza")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analisis, container, false)

        // Inicializar vistas
        etPeso = view.findViewById(R.id.et_peso)
        spinnerNivelCondicion = view.findViewById(R.id.spinner_nivel_condicion)
        etTiempoDisponible = view.findViewById(R.id.et_tiempo_disponible)
        spinnerObjetivo = view.findViewById(R.id.spinner_objetivo)
        btnCalcularRutina = view.findViewById(R.id.btn_calcular_rutina)
        btnGuardarResultados = view.findViewById(R.id.btn_guardar_resultados)
        tvCaloriasQuemadas = view.findViewById(R.id.tv_calorias_quemadas)
        tvDistribucionEjercicios = view.findViewById(R.id.tv_distribucion_ejercicios)
        tvIntensidadRecomendada = view.findViewById(R.id.tv_intensidad_recomendada)

        // Setup de los Spinners
        val adapterNivel = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nivelesCondicion)
        adapterNivel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNivelCondicion.adapter = adapterNivel

        val adapterObjetivo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, objetivos)
        adapterObjetivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerObjetivo.adapter = adapterObjetivo

        sharedPreferences = requireContext().getSharedPreferences("RutinaData", Context.MODE_PRIVATE)

        btnCalcularRutina.setOnClickListener {
            calcularRutina()
        }

        btnGuardarResultados.setOnClickListener {
            guardarResultados()
        }

        return view
    }

    private fun calcularRutina() {
        // Obtener datos de los campos
        val peso = etPeso.text.toString().toFloatOrNull()
        val tiempoDisponible = etTiempoDisponible.text.toString().toIntOrNull()
        val nivelCondicion = spinnerNivelCondicion.selectedItemPosition // 0, 1, 2 (Principiante, Intermedio, Avanzado)
        val objetivo = spinnerObjetivo.selectedItemPosition // 0, 1, 2 (Pérdida de peso, Tonificación, Fuerza)

        if (peso == null || tiempoDisponible == null) {
            Toast.makeText(requireContext(), "Por favor ingresa datos válidos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Cálculo de calorías a quemar
        val multiplicadorNivel = when (nivelCondicion) {
            0 -> 1.2f // Principiante
            1 -> 1.5f // Intermedio
            2 -> 1.8f // Avanzado
            else -> 1.2f
        }
        val caloriasQuemadas = (peso * multiplicadorNivel * tiempoDisponible / 30)

        // Distribución de ejercicios (porcentaje según objetivo)
        val porcentajeEjercicio = when (objetivo) {
            0 -> 0.7f // Pérdida de peso
            1 -> 0.5f // Tonificación
            2 -> 0.6f // Fuerza
            else -> 0.5f
        }
        val tiempoEjercicio = tiempoDisponible * porcentajeEjercicio

        // Intensidad recomendada (basado en objetivo y nivel)
        val intensidadRecomendada = when {
            objetivo == 0 && nivelCondicion == 2 -> "Alta"
            objetivo == 2 && nivelCondicion == 0 -> "Baja"
            else -> "Media"
        }

        // Mostrar resultados
        tvCaloriasQuemadas.text = "Calorías a quemar: ${caloriasQuemadas.toInt()}"
        tvDistribucionEjercicios.text = "Distribución de ejercicios: ${tiempoEjercicio.toInt()} min"
        tvIntensidadRecomendada.text = "Intensidad recomendada: $intensidadRecomendada"
    }

    private fun guardarResultados() {
        val calorias = tvCaloriasQuemadas.text.toString()
        val distribucion = tvDistribucionEjercicios.text.toString()
        val intensidad = tvIntensidadRecomendada.text.toString()

        // Obtener la fecha actual
        val fecha = getCurrentDate()

        // Crear un objeto JSON para la rutina
        val rutina = JSONObject()
        rutina.put("calorias", calorias)
        rutina.put("distribucion", distribucion)
        rutina.put("intensidad", intensidad)
        rutina.put("fecha", fecha) // Agregar la fecha a la rutina

        // Recuperar las rutinas previas almacenadas
        val listaRutinasJson = sharedPreferences.getString("listaRutinas", "[]")
        val listaRutinas = JSONArray(listaRutinasJson)

        // Agregar la nueva rutina a la lista
        listaRutinas.put(rutina)

        // Guardar la lista actualizada en SharedPreferences
        with(sharedPreferences.edit()) {
            putString("listaRutinas", listaRutinas.toString())
            apply()
        }

        Toast.makeText(requireContext(), "Resultados guardados", Toast.LENGTH_SHORT).show()

        // Navegar de regreso a la pantalla anterior
        findNavController().popBackStack()
    }

    // Método para obtener la fecha actual en formato deseado
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Formato de fecha: Año-Mes-Día
        return dateFormat.format(Date()) // Devuelve la fecha actual como String
    }
}
