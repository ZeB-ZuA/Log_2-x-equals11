package com.udistrital.log_2xequals11.Repository

import com.udistrital.log_2xequals11.ViewModel.BoardViewModel

interface BoardRepository {

    fun save(boardViewModel: BoardViewModel)
    fun fetchBoard(callback: (BoardViewModel?) -> Unit)


}