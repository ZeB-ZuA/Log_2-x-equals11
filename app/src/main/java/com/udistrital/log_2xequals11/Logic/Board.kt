package com.udistrital.log_2xequals11.Logic

class Board(val size: Int = 4) {
    var board: Array<Array<Tile>> = Array(size) { Array(size) { Tile() } }

    init {
        addNewNumber()
        addNewNumber()
    }

    fun start() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                board[i][j] = Tile()
            }
        }
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

        val (x, y) = emptyCells[(Math.random() * emptyCells.size).toInt()]
        val value = if (Math.random() < 0.9) 2 else 4
        board[x][y] = Tile(value)
    }

    fun moveLeft() {
        for (i in 0 until size) {
            val nonZeroTiles = board[i].filter { it.value != 0 }

            for (j in nonZeroTiles.indices) {
                board[i][j] = nonZeroTiles[j]
            }

            for (j in nonZeroTiles.size until size) {
                board[i][j] = Tile()
            }
        }
    }



    fun moveRight() {
        println("RIGHT")
    }

    fun moveUp() {
        println("UP")
    }

    fun moveDown() {
        println("DOWN")
    }
    fun printBoard() {
        for (row in board) {
            println(row.joinToString { it.value.toString() })
        }
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

}
