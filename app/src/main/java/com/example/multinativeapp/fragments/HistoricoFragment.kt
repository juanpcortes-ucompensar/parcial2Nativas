package com.example.multinativeapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.multinativeapp.R
import com.example.multinativeapp.adapters.HistoricoAdapter
import com.example.multinativeapp.models.RutinaItem
import org.json.JSONArray

class HistoricoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotalRutinas: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historico, container, false)

        recyclerView = view.findViewById(R.id.recycler_historial_rutinas)
        tvTotalRutinas = view.findViewById(R.id.tv_total_rutinas)

        sharedPreferences = requireContext().getSharedPreferences("RutinaData", Context.MODE_PRIVATE)

        cargarHistorialRutinas()

        return view
    }

    private fun cargarHistorialRutinas() {
        // Recuperar las rutinas guardadas en SharedPreferences
        val listaRutinasJson = sharedPreferences.getString("listaRutinas", "[]")
        val listaRutinas = JSONArray(listaRutinasJson)
        val rutinas = mutableListOf<RutinaItem>()

        for (i in 0 until listaRutinas.length()) {
            val rutinaJson = listaRutinas.getJSONObject(i)
            val calorias = rutinaJson.getString("calorias")
            val distribucion = rutinaJson.getString("distribucion")
            val intensidad = rutinaJson.getString("intensidad")
            val fecha = rutinaJson.getString("fecha")

            // Añadimos el número de rutina aquí
            val numeroRutina = i + 1

            rutinas.add(RutinaItem(calorias, distribucion, intensidad, fecha, numeroRutina))
        }

        // Actualizar el RecyclerView
        val adapter = HistoricoAdapter(rutinas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Actualizar el total de rutinas
        tvTotalRutinas.text = "Total de rutinas: ${rutinas.size}"
    }
}
