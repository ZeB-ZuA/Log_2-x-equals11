package com.udistrital.log_2xequals11.Service

import com.google.firebase.database.FirebaseDatabase
import com.udistrital.log_2xequals11.Logic.Board
import com.udistrital.log_2xequals11.Logic.Tile
import com.udistrital.log_2xequals11.Repository.BoardRepository

class BoardService : BoardRepository {
    private val db = FirebaseDatabase.getInstance()

    override fun save(board: Board) {
        try {
            val ref = db.getReference("main_game")
            val boardData = board.toList().map { row ->
                row.map { it.value }
            }
            val mainGame = mapOf(
                "board" to boardData,
                "score" to board.score
            )
            ref.setValue(mainGame).addOnSuccessListener {
                println("Tablero guardado exitosamente")
            }.addOnFailureListener { e ->
                println("Error al guardar el tablero: ${e.message}")
            }
        } catch (e: Exception) {
            println("Error al guardar el tablero: ${e.message}")
        }
    }

     override fun fetchBoard(callback: (Board?) -> Unit) {
        try {
            val ref = db.getReference("main_game")
            ref.get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val mainGame = dataSnapshot.value as Map<String, Any>
                    val boardData = mainGame["board"] as List<List<Long>>
                    val score = (mainGame["score"] as Long).toInt()
                    val board = Board()
                    for (i in 0 until board.size) {
                        for (j in 0 until board.size) {
                            board.board[i][j] = Tile(boardData[i][j].toInt())
                        }
                    }
                    board.score = score
                    callback(board)
                } else {
                    println("No se encontró ningún tablero en la base de datos.")
                    callback(null)
                }
            }.addOnFailureListener { e ->
                println("Error al obtener el tablero: ${e.message}")
                callback(null)
            }
        } catch (e: Exception) {
            println("Error al obtener el tablero: ${e.message}")
            callback(null)
        }
    }
}

