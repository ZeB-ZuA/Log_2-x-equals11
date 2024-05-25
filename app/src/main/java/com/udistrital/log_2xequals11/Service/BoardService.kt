package com.udistrital.log_2xequals11.Service

import com.google.firebase.database.FirebaseDatabase
import com.udistrital.log_2xequals11.Logic.Board
import com.udistrital.log_2xequals11.Repository.BoardRepository

class BoardService: BoardRepository {
    private val db = FirebaseDatabase.getInstance()

    override fun save(board: Board) {
        try {
            val ref = db.getReference("boards")
            ref.setValue(board.toList())
        } catch (e: Exception) {
            println("Error al guardar el tablero: ${e.message}")
        }
    }
}