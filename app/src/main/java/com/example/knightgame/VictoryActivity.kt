package com.example.knightgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class VictoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_victory)

        // Botón para reiniciar el juego o volver a la pantalla de inicio
        val restartButton: Button = findViewById(R.id.restartButton)
        restartButton.setOnClickListener {
            // Reiniciar el juego y volver a la pantalla de inicio
            val intent = Intent(this, StartScreenActivity::class.java)
            startActivity(intent)
            finish() // Cerrar la pantalla de victoria
        }
    }
}
