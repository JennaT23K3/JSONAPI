package com.example.jsonapi.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonapi.model.Todo
import com.example.jsonapi.model.TodosApi
import kotlinx.coroutines.launch

sealed interface TodoUIState {
    data class Success(val todos: List<Todo>) : TodoUIState
    data object Error: TodoUIState
    data object Loading: TodoUIState
}

class TodoViewModel: ViewModel() {
    var todoUIState : TodoUIState by mutableStateOf <TodoUIState> (TodoUIState.Loading)
        private set

    init{
        getTodosList()
    }

    private fun getTodosList(){
        viewModelScope.launch {
            var todosApi: TodosApi? = null
            try{
                todosApi = TodosApi!!.getInstance()
                todoUIState = TodoUIState.Success(todosApi.getTodos())
            }catch (e: Exception) {
                Log.d("VIEWMODEL", e.message.toString())
                todoUIState = TodoUIState.Error
            }
        }
    }
}
