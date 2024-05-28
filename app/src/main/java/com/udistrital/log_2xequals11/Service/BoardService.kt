package com.udistrital.log_2xequals11.Service

import com.google.firebase.database.FirebaseDatabase
import com.udistrital.log_2xequals11.Logic.Board
import com.udistrital.log_2xequals11.Repository.BoardRepository

class BoardService: BoardRepository {
    private val db = FirebaseDatabase.getInstance()

    override fun save(board: Board) {
        try {
            val ref = db.getReference("main_game")
            val mainGame = mapOf(
                "board" to board.toList(),
                "score" to board.score
            )
            ref.setValue(mainGame)
        } catch (e: Exception) {
            println("Error al guardar el tablero: ${e.message}")
        }
    }
}