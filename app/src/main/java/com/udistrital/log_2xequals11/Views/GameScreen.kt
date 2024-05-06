package com.udistrital.log_2xequals11.Views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.udistrital.log_2xequals11.Logic.Board
import com.udistrital.log_2xequals11.Logic.Tile




@Composable
fun GameScreen(navController: NavController) {
    val board = remember {
        mutableStateOf(Board())
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val newBoard = Board()
            newBoard.start()
            board.value = newBoard
        }) {
            Text("RESTART")
        }

        BoardView(board)

        Directions(board)
    }
}

@Composable
fun Directions(boardState: MutableState<Board>) {
    var board = boardState.value
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            val newBoard = boardState.value.copy()
            newBoard.moveLeft()
            boardState.value = newBoard

        }) {
            Text("IZQUIERDA")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { }) {
                Text("ARRIBA")
            }

            Button(onClick = { }) {
                Text("ABAJO")
            }
        }

        Button(onClick = { }) {
            Text("DERECHA")
        }
    }
}


@Composable
fun BoardView(boardState: MutableState<Board>) {
    val board = boardState.value
    Column {
        for (row in board.board) {
            Row {
                for (tile in row) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .border(2.dp, Color.Black)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (tile.value == 0) "" else tile.value.toString())
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun GameScreenPreview() {

    val navController = rememberNavController()

    GameScreen(navController = navController)
}







