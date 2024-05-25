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

    private fun compress(board: Array<Array<Tile>>): Array<Array<Tile>> {
        val newBoard = Array(size) { Array(size) { Tile() } }
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

    private fun merge(board: Array<Array<Tile>>): Array<Array<Tile>> {
        for (i in 0 until size) {
            for (j in 0 until size - 1) {
                if (board[i][j].value != 0 && board[i][j].value == board[i][j + 1].value) {
                    board[i][j].value *= 2
                    board[i][j + 1] = Tile()
                }
            }
        }
        return board
    }

    private fun reverse(board: Array<Array<Tile>>): Array<Array<Tile>> {
        val newBoard = Array(size) { Array(size) { Tile() } }
        for (i in 0 until size) {
            for (j in 0 until size) {
                newBoard[i][j] = board[i][size - 1 - j]
            }
        }
        return newBoard
    }

    private fun transpose(board: Array<Array<Tile>>): Array<Array<Tile>> {
        val newBoard = Array(size) { Array(size) { Tile() } }
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

    fun refreshBoard(tempBoard: Array<Array<Tile>>) {
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = tempBoard[i][j]
            }
        }
    }
}
