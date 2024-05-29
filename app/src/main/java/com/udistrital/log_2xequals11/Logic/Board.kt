package com.udistrital.log_2xequals11.Logic

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.udistrital.log_2xequals11.Service.BoardService


class Board(val size: Int = 4) {
    var board: MutableList<MutableList<Tile>> = MutableList(size) { MutableList(size) { Tile() } }
    var score: Int = 0
    val boardService = BoardService()
    init {
        
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


    fun getFromFirebase(callback: (Board?) -> Unit) {
        boardService.fetchBoard { board ->
            callback(board)
        }
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
                    score += newBoard[i][j].value
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
        println("Score: $score")
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
        newBoard.score = this.score
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

    private fun refreshBoard(tempBoard: List<List<Tile>>) {
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = tempBoard[i][j]

            }
        }
    }

    fun toList(): List<List<Tile>> {
        return board
    }
    fun isWon(): Boolean {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (board[i][j].value == 2048) {
                    println("HAS GANADO")
                    return true
                }
            }
        }
        return false
    }

    fun isLost(): Boolean {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (board[i][j].value == 0) {
                    return false
                }
                if (i < size - 1 && board[i][j].value == board[i + 1][j].value) {
                    return false
                }
                if (j < size - 1 && board[i][j].value == board[i][j + 1].value) {
                    return false
                }
            }
        }
        println("HAS PERDIDO")
        return true
    }



}
