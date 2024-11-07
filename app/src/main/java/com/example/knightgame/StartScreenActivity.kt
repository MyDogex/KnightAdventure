package com.example.knightgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        // Botón para comenzar el juego
        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            // Lanzar MainActivity cuando el jugador presione el botón
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cerrar la pantalla de inicio
        }
    }
}
