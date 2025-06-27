package com.example.propietariopeluditosfelices

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PantallaInicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio)

        // Referencia el botón "Agenda"
        val btnAgenda = findViewById<Button>(R.id.buttonAgenda)

        // Configura el clic del botón
        btnAgenda.setOnClickListener {
            val intent = Intent(this, PantallaAgenda::class.java)
            startActivity(intent)
            finish()
        }
    }
}