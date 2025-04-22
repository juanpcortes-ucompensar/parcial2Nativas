package com.example.multinativeapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.multinativeapp.R

class InicioFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        val tvBienvenida = view.findViewById<TextView>(R.id.tv_bienvenida)
        sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        val nombreUsuario = sharedPreferences.getString("nombre","")
        tvBienvenida.text = "¡Bienvenido a la Parcial 2, $nombreUsuario!"

        return view
    }
}
