package com.udistrital.log_2xequals11.Logic

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue


class Board(val size: Int = 4) {
    var board: MutableList<MutableList<Tile>> = MutableList(size) { MutableList(size) { Tile() } }

    init {
        getBoardFromFirebase()
        
    }


    fun getBoardFromFirebase() {
        try {
            val database = Firebase.database
            val myRef = database.getReference("boards")

            val boardListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val firebaseBoard = dataSnapshot.getValue<List<List<Tile>>>()
                    println("Tablero de Firebase: $firebaseBoard")
                    if (firebaseBoard != null) {
                        refreshBoard(firebaseBoard)
                        println()
                    } else {
                        println("El tablero de Firebase está vacío.")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("Error al leer el tablero de Firebase: ${databaseError.toException()}")
                }
            }
            myRef.addValueEventListener(boardListener)
        } catch (e: Exception) {
            println("Ocurrió un error al obtener el tablero de Firebase: ${e.message}")
        }
    }


    fun start() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                board[i][j] = Tile()
            }
        }
        println("START/RESTART")
        addNewNumber()
        addNewNumber()
    }


    private fun addNewNumber() {
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (board[i][j].value == 0) {
                    emptyCells.add(Pair(i, j))
                }
            }
        }
        if (emptyCells.isNotEmpty()) {
            val (x, y) = emptyCells[(Math.random() * emptyCells.size).toInt()]
            val value = if (Math.random() < 0.9) 2 else 4
            board[x][y] = Tile(value)
        }
    }

    private fun compress(board: List<List<Tile>>): List<List<Tile>> {
        val newBoard = MutableList(size) { MutableList(size) { Tile() } }
        for (i in 0 until size) {
            var pos = 0
            for (j in 0 until size) {
                if (board[i][j].value != 0) {
                    newBoard[i][pos] = board[i][j]
                    pos++
                }
            }
        }
        return newBoard
    }

    private fun merge(board: List<List<Tile>>): List<List<Tile>> {
        val newBoard = board.map { it.toMutableList() }
        for (i in 0 until size) {
            for (j in 0 until size - 1) {
                if (newBoard[i][j].value != 0 && newBoard[i][j].value == newBoard[i][j + 1].value) {
                    newBoard[i][j].value *= 2
                    newBoard[i][j + 1] = Tile()
                }
            }
        }
        return newBoard
    }

    private fun reverse(board: List<List<Tile>>): List<List<Tile>> {
        val newBoard = MutableList(size) { MutableList(size) { Tile() } }
        for (i in 0 until size) {
            for (j in 0 until size) {
                newBoard[i][j] = board[i][size - 1 - j]
            }
        }
        return newBoard
    }

    private fun transpose(board: List<List<Tile>>): List<List<Tile>> {
        val newBoard = MutableList(size) { MutableList(size) { Tile() } }
        for (i in 0 until size) {
            for (j in 0 until size) {
                newBoard[i][j] = board[j][i]
            }
        }
        return newBoard
    }

    fun printBoard() {
        for (row in board) {
            println(row.joinToString(" ") { if (it.value == 0) "." else it.value.toString() })
        }
        println()
    }

    fun copy(): Board {
        val newBoard = Board()
        for (i in 0 until size) {
            for (j in 0 until size) {
                newBoard.board[i][j] = this.board[i][j].copy()
            }
        }
        return newBoard
    }

    fun moveLeft() {
        var tempBoard = compress(board)
        tempBoard = merge(tempBoard)
        tempBoard = compress(tempBoard)
        refreshBoard(tempBoard)
        addNewNumber()
        println("LEFT")
        printBoard()
    }

    fun moveRight() {
        var tempBoard = reverse(board)
        tempBoard = compress(tempBoard)
        tempBoard = merge(tempBoard)
        tempBoard = compress(tempBoard)
        tempBoard = reverse(tempBoard)
        refreshBoard(tempBoard)
        addNewNumber()
        println("RIGHT")
        printBoard()
    }

    fun moveUp() {
        var tempBoard = transpose(board)
        tempBoard = compress(tempBoard)
        tempBoard = merge(tempBoard)
        tempBoard = compress(tempBoard)
        tempBoard = transpose(tempBoard)
        refreshBoard(tempBoard)
        addNewNumber()
        println("UP")
        printBoard()
    }

    fun moveDown() {
        var tempBoard = transpose(board)
        tempBoard = reverse(tempBoard)
        tempBoard = compress(tempBoard)
        tempBoard = merge(tempBoard)
        tempBoard = compress(tempBoard)
        tempBoard = reverse(tempBoard)
        tempBoard = transpose(tempBoard)
        refreshBoard(tempBoard)
        addNewNumber()
        println("DOWN")
        printBoard()
    }

    fun refreshBoard(tempBoard: List<List<Tile>>) {
        println("Refrescando el tablero...")
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = tempBoard[i][j]
                println("Nuevo valor: ${board[i][j].value}")
            }
        }
        println("Tablero refrescado.")
    }

    fun toList(): List<List<Tile>> {
        return board
    }


}
