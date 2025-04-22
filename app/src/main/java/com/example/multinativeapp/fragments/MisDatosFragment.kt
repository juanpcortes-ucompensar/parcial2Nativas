package com.example.multinativeapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.multinativeapp.R
import androidx.navigation.fragment.findNavController

class MisDatosFragment : Fragment() {

    private lateinit var textViewName: TextView
    private lateinit var textViewAge: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewProgram: TextView
    private lateinit var textViewSemester: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mis_datos, container, false)

        sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        // Limpiar SharedPreferences al iniciar la app
        limpiarSharedPreferences()

        // Referencias a los nuevos TextViews
        textViewName = view.findViewById(R.id.tv_name)
        textViewAge = view.findViewById(R.id.tv_age)
        textViewEmail = view.findViewById(R.id.tv_email)
        textViewProgram = view.findViewById(R.id.tv_program)
        textViewSemester = view.findViewById(R.id.tv_semester)

        guardarDatosUsuario()
        llenarDatosUsuario()

        return view
    }

    private fun limpiarSharedPreferences() {
        with(sharedPreferences.edit()) {
            remove("listaRutinas") // Elimina únicamente la clave "listaRutinas"
            apply() // Aplica los cambios
        }
    }

    private fun guardarDatosUsuario() {
        val sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("nombre", "Juan Pablo Cortes")
        editor.putString("edad", "22")
        editor.putString("correo", "juanpcortes@ucompensar.edu.co")
        editor.putString("programa", "Ingeniería de Software")
        editor.putString("semestre", "Octavo")

        editor.apply() // O editor.commit()
    }

    private fun llenarDatosUsuario() {
        val nombre = sharedPreferences.getString("nombre", "")
        val edad = sharedPreferences.getString("edad", "")
        val correo = sharedPreferences.getString("correo", "")
        val programa = sharedPreferences.getString("programa", "")
        val semestre = sharedPreferences.getString("semestre", "")

        textViewName.text = "Nombre: $nombre"
        textViewAge.text = "Edad: $edad"
        textViewEmail.text = "Correo Institucional: $correo"
        textViewProgram.text = "Programa Académico: $programa"
        textViewSemester.text = "Semestre: $semestre"
    }

    override fun onResume() {
        super.onResume()
        llenarDatosUsuario()
    }
}
