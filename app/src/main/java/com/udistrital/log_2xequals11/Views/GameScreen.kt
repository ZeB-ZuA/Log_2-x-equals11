package com.udistrital.log_2xequals11.Views

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.udistrital.log_2xequals11.Logic.Board
import com.udistrital.log_2xequals11.Service.BoardService
import com.udistrital.log_2xequals11.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun InitBoard(board:  MutableState<Board>, boardService: BoardService, context: Context){
    val newBoard = Board()
    newBoard.start()
    board.value = newBoard
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun GameScreen(navController: NavController) {
    val board = mutableStateOf(Board())
    val boardService = BoardService()
    val colorBtn = colorResource(id = R.color.rose)
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        InitBoard(board, boardService, context)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(50.dp))
        HeadPage(board)
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            Button(onClick = {
                val newBoard = Board()
                newBoard.start()
                board.value = newBoard
                boardService.save(board.value)
            },
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = colorBtn
                    ),) {
                Text("RESTART")
            }

            Button(
                onClick = {
                    val newBoard = Board()
                    newBoard.getFromFirebase { fetchedBoard ->
                        if (fetchedBoard != null) {
                            board.value = fetchedBoard
                            boardService.save(board.value)
                        } else {
                           println("ERROR EN RECUPARAR GAMESCRENN LINE 85")
                        }
                    }
                },
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = colorBtn
                    ),) {
                Text("💾")
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
        BoardView(board)
        Spacer(modifier = Modifier.size(10.dp))
        Directions(board)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Directions(boardState: MutableState<Board>) {
    val boardService = BoardService()
    val colorBtnRose = colorResource(id = R.color.rose)
    val colorBtnHoney = colorResource(id = R.color.honeydew)
    val colorBtn = colorResource(id = R.color.almond)

    if(boardState.value.isLost()){
        Text(text = "Has perdido 🥴", color = colorResource(id = R.color.rose), fontSize = 30.sp)
    } else if(boardState.value.isWon()){
        Text(text = "Has ganado 🏆", color = colorResource(id = R.color.yellow), fontSize = 30.sp)
    } else {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = colorBtn
                ) ,
            onClick = {
            val newBoard = boardState.value.copy()
            newBoard.moveLeft()
            boardState.value = newBoard
            boardService.save(boardState.value)
                ToastMsg(boardState)
        }) {
            Text("IZQUIERDA")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                val newBoard = boardState.value.copy()
                newBoard.moveUp()
                boardState.value = newBoard
                boardService.save(boardState.value)
                ToastMsg(boardState)

            },
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = colorBtnRose
                    ) ,
                ) {
                Text("ARRIBA")
            }

            Button(onClick = {
                val newBoard = boardState.value.copy()
                newBoard.moveDown()
                boardState.value = newBoard
                boardService.save(boardState.value)
                ToastMsg(boardState)
            },  colors = ButtonDefaults
                .buttonColors(
                    containerColor = colorBtnHoney
                ) ,) {

                Text("ABAJO", fontWeight = FontWeight.Bold)
            }
        }

        Button(onClick = {
            val newBoard = boardState.value.copy()
            newBoard.moveRight()
            boardState.value = newBoard
            boardService.save(boardState.value)
            ToastMsg(boardState)
        },
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = colorBtn
                ) ,) {

            Text("DERECHA")
        }
    }
    }
}

fun ToastMsg(boardState: MutableState<Board>){
    val showToast = mutableStateOf(false)
    val toastMessage = mutableStateOf("")
    if (boardState.value.isWon()){
        toastMessage.value = "¡Has ganado!"
        showToast.value = true
    } else if (boardState.value.isLost()){
        toastMessage.value = "Has perdido"
        showToast.value = true
    }
}


@Composable
fun BoardView(boardState: MutableState<Board>) {
    val board = boardState.value;

    Column {
        for (row in board.board) {
            Row {
                for (tile in row) {
                    val color =  when (tile.value) {
                        0 -> R.color.alice
                        64,2048 -> R.color.honeydew
                        2,512 -> R.color.yellow
                        4,128, 4096 -> R.color.aquamarine
                        8,256, 8192 -> R.color.pale
                        16,16384 -> R.color.blue
                        32,1024 -> R.color.rose

                        else -> R.color.beige
                    }

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(colorResource(id = color))
                            .border(3.dp, colorResource(R.color.columbia))
                            .padding(10.dp),

                        contentAlignment = Alignment.Center

                    ) {
                        Text(
                            color  = colorResource(id = R.color.black),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            text = if (tile.value == 0) "" else tile.value.toString())
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

@Composable
fun HeadPage(boardState: MutableState<Board>) {
    Row {
        Text(
            text = "20",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.rose)
        )
        Text(
            text = "48",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.honeydew)
        )
        Spacer(modifier = Modifier.size(30.dp))
        Box(
            modifier = Modifier
                .background(colorResource(id = R.color.rose))
                .border(
                    1.dp, colorResource(id = R.color.white),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            Text(
                "${boardState.value.score.toString().padStart(4, '0')}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white)
            )
        }
    }
}









