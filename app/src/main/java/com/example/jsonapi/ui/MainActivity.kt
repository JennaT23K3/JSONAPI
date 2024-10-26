@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jsonapi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jsonapi.model.Todo
import com.example.jsonapi.ui.theme.JSONAPITheme
import com.example.jsonapi.viewmodel.TodoUIState
import com.example.jsonapi.viewmodel.TodoViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JSONAPITheme {
                TodoApp()
            }
        }
    }
}



@Composable
fun TodoApp(todoViewModel: TodoViewModel = TodoViewModel()){
    TodoScreen(uiState = todoViewModel.todoUIState)
    Scaffold(
        topBar = { TopAppBar(
            title = { Text("Todos")}
        )
        },
        content = {
            TodoScreen(uiState = todoViewModel.todoUIState)
        }
    )
}

@Composable
fun TodoScreen(uiState: TodoUIState) {
    when (uiState){
        is TodoUIState.Loading -> LoadingScreen()
        is TodoUIState.Success -> TodoList(uiState.todos)
        is TodoUIState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen(){ Text("Loading...") }
@Composable
fun ErrorScreen(){ Text("Error retrieving data from API.") }

@Composable
fun TodoList(todos: List<Todo>) {
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ){
        items(todos) { todo ->
            Text(
                text = todo.title,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }
    }
}

