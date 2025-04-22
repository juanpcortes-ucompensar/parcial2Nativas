package com.example.multinativeapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.multinativeapp.R
import com.example.multinativeapp.models.RutinaItem

class HistoricoAdapter(private val listaRutinas: List<RutinaItem>) :
    RecyclerView.Adapter<HistoricoAdapter.RutinaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rutina, parent, false)
        return RutinaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int) {
        val rutina = listaRutinas[position]

        // Asignar el número de la rutina
        holder.tvTituloRutina.text = "Rutina ${position + 1}" // Aquí el número comienza desde 1

        holder.tvCalorias.text = "Calorías quemadas: ${rutina.calorias}"
        holder.tvDistribucion.text = "Distribución de ejercicios: ${rutina.distribucion}"
        holder.tvIntensidad.text = "Intensidad recomendada: ${rutina.intensidad}"
        holder.tvFecha.text = "Fecha: ${rutina.fecha}"
    }

    override fun getItemCount(): Int {
        return listaRutinas.size
    }

    class RutinaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTituloRutina: TextView = itemView.findViewById(R.id.tv_titulo_rutina) // Este es el TextView para el título de la rutina
        val tvCalorias: TextView = itemView.findViewById(R.id.tv_calorias)
        val tvDistribucion: TextView = itemView.findViewById(R.id.tv_distribucion)
        val tvIntensidad: TextView = itemView.findViewById(R.id.tv_intensidad)
        val tvFecha: TextView = itemView.findViewById(R.id.tv_fecha)
    }
}
