package com.example.propietariopeluditosfelices.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.propietariopeluditosfelices.R
import com.example.propietariopeluditosfelices.modelo.Cita

class CitasAdapter(
    private val onClickCita: (Cita) -> Unit, // Función lambda para manejar clics
) : RecyclerView.Adapter<CitasAdapter.CitasViewHolder>() {

    private val citas: MutableList<Cita> = mutableListOf() // Lista dinámica de citas

    // ViewHolder interno
    inner class CitasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvServicio: TextView = itemView.findViewById(R.id.tvServicio)
        private val tvnombreUsuario: TextView = itemView.findViewById(R.id.tvNombreUsuario)
        private val tvNombreMascota: TextView = itemView.findViewById(R.id.tvNombreMascota)
        private val tvfechaHora: TextView = itemView.findViewById(R.id.tvfechaHora)

        fun bind(cita: Cita) {
            tvServicio.text = "Servicio: ${cita.idServicio}"
            tvnombreUsuario.text = "Nombre: ${cita.nombreCompletoUsuario}"
            tvNombreMascota.text = "Mascota: ${cita.mascota}"
            tvfechaHora.text = "Fecha: ${cita.dia} - ${cita.hora}"

            itemView.setOnClickListener { onClickCita(cita) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_citas, parent, false)
        return CitasViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitasViewHolder, position: Int) {
        holder.bind(citas[position])
    }

    override fun getItemCount(): Int = citas.size

    // Método para actualizar la lista de citas
    fun actualizarCitas(nuevasCitas: List<Cita>) {
        citas.clear()
        citas.addAll(nuevasCitas)
        notifyDataSetChanged()
    }
}
