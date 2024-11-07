package com.example.knightgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var lives = 3 // Vidas del jugador
    private var victories = 0 // Contador de victorias
    private var wins = 0 // Contador de victorias en total
    private var currentPlayer = "X" // Jugador actual (X para el caballero)
    private val buttons = Array(3) { arrayOfNulls<Button>(3) } // Tablero de botones
    private val board = Array(3) { IntArray(3) } // Tablero de juego (0 vacío, 1 X, -1 O)

    // Vistas de los elementos del juego
    private lateinit var livesText: TextView
    private lateinit var heart1: ImageView
    private lateinit var heart2: ImageView
    private lateinit var heart3: ImageView
    private lateinit var knightImage: ImageView
    private lateinit var enemyImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar las vistas
        livesText = findViewById(R.id.livesText)
        heart1 = findViewById(R.id.heart1)
        heart2 = findViewById(R.id.heart2)
        heart3 = findViewById(R.id.heart3)
        knightImage = findViewById(R.id.knightImage)
        enemyImage = findViewById(R.id.enemyImage)

        // Inicializar los botones del tablero
        val gridLayout = findViewById<GridLayout>(R.id.ticTacToeBoard)
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val buttonID = "button$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons[i][j] = findViewById(resID)
                buttons[i][j]?.setOnClickListener {
                    onCellClicked(i, j)
                }
            }
        }
    }

    private fun onCellClicked(i: Int, j: Int) {
        if (board[i][j] != 0) return // Si ya hay algo en la casilla, no hacer nada

        // Hacer el movimiento del caballero (X)
        board[i][j] = 1
        buttons[i][j]?.apply {
            text = "X"
            isEnabled = false
        }

        // Verificar si el caballero ha ganado
        if (checkWinner()) {
            wins++ // Aumentamos el contador de victorias
            if (wins == 3) { // Si el caballero gana 3 veces
                livesText.text = "¡Ganaste el juego!" // Mostrar el mensaje de victoria
                showVictoryScreen() // Redirigir a la pantalla de victoria
            } else {
                livesText.text = "¡Ganaste una ronda!"
            }
            resetBoard() // Reiniciar el tablero para la siguiente ronda
        } else {
            // Si no gana, es el turno del enemigo
            currentPlayer = "O"
            enemyTurn()
        }
    }

    private fun enemyTurn() {
        // Movimiento aleatorio del enemigo (O)
        var i: Int
        var j: Int
        do {
            i = Random.nextInt(3)
            j = Random.nextInt(3)
        } while (board[i][j] != 0) // Repetir hasta encontrar una casilla vacía

        board[i][j] = -1
        buttons[i][j]?.apply {
            text = "O"
            isEnabled = false
        }

        // Verificar si el enemigo ha ganado
        if (checkWinner()) {
            lives-- // Restar una vida
            updateHearts() // Actualizar los corazones
            livesText.text = "¡Perdiste una vida!"
            if (lives == 0) {
                livesText.text = "¡Perdiste el juego!"
                showGameOverScreen() // Mostrar la pantalla de Game Over
            }
            resetBoard() // Reiniciar el tablero
        } else {
            currentPlayer = "X" // Volver al turno del caballero
        }
    }

    private fun showVictoryScreen() {
        // Iniciar la actividad VictoryActivity cuando el caballero gane 3 veces
        val intent = Intent(this, VictoryActivity::class.java)
        startActivity(intent)
        finish() // Cerrar MainActivity
    }

    private fun showGameOverScreen() {
        // Iniciar la actividad GameOverActivity cuando se pierden todas las vidas
        val intent = Intent(this, GameOverActivity::class.java)
        startActivity(intent)
        finish() // Cerrar MainActivity
    }

    private fun checkWinner(): Boolean {
        // Verificar si el jugador ha ganado

        // Verificar las filas
        for (i in 0 until 3) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
                return true
            }
        }

        // Verificar las columnas
        for (j in 0 until 3) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[0][j] != 0) {
                return true
            }
        }

        // Verificar las diagonales
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            return true
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) {
            return true
        }

        return false
    }

    private fun resetBoard() {
        // Limpiar el tablero
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                board[i][j] = 0
                buttons[i][j]?.apply {
                    text = ""
                    isEnabled = true
                }
            }
        }
    }

    private fun updateHearts() {
        // Actualizar las imágenes de los corazones según las vidas
        when (lives) {
            2 -> heart3.setImageResource(R.drawable.ic_heart_empty)
            1 -> heart2.setImageResource(R.drawable.ic_heart_empty)
            0 -> heart1.setImageResource(R.drawable.ic_heart_empty)
        }
    }

    private fun resetGame() {
        // Reiniciar el juego completo (vidas, victorias, tablero)
        lives = 3
        wins = 0
        heart1.setImageResource(R.drawable.ic_heart_full)
        heart2.setImageResource(R.drawable.ic_heart_full)
        heart3.setImageResource(R.drawable.ic_heart_full)
        livesText.text = "Vidas: 3"
        resetBoard()
    }
}

