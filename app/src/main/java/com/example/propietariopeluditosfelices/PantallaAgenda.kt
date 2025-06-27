package com.example.propietariopeluditosfelices

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.propietariopeluditosfelices.modelo.Cita
import com.example.propietariopeluditosfelices.viewadapters.CitasAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PantallaAgenda : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var recyclerViewCitas: RecyclerView
    private lateinit var citasAdapter: CitasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_agenda)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@PantallaAgenda, PantallaInicio::class.java)
                startActivity(intent)
                finish()
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)

        // Configurar español como idioma predeterminado
        val locale = Locale("es", "ES")
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)

        // Inicializar vistas
        calendarView = findViewById(R.id.calendarView)
        recyclerViewCitas = findViewById(R.id.recyclerViewCitas)

        // Configurar RecyclerView
        citasAdapter = CitasAdapter { cita ->
        }
        recyclerViewCitas.adapter = citasAdapter
        recyclerViewCitas.layoutManager = LinearLayoutManager(this)

        val calendar = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
        }

        // Manejar selección de fechas
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            cargarCitas(calendar)
        }

        cargarCitas(Calendar.getInstance())
    }


    private fun cargarCitas(calendar: Calendar) {

        val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dia = formatoFecha.format(calendar.time)

        FirestoreDBConnector.leerCitas(dia,
            callback = { result, error ->
                if (error != null) {
                    Toast.makeText(this, "Error al consultar las citas", Toast.LENGTH_SHORT).show()
                } else if (result != null && !result.isEmpty) {
                    val listaCitas = result.documents.mapNotNull { document ->
                        document.toObject(Cita::class.java) // Convierte el documento a un objeto Cita
                    }
                    citasAdapter.actualizarCitas(listaCitas) // Actualiza el adaptador con la lista de citas
                } else {
                    Toast.makeText(this, "No se encontraron citas aún", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
        // Botón "Flecha" para volver
        findViewById<ImageButton>(R.id.ButtonVolver).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
