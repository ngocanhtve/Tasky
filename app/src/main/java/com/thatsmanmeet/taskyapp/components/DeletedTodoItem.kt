package com.thatsmanmeet.taskyapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.thatsmanmeet.taskyapp.room.Todo
import com.thatsmanmeet.taskyapp.room.TodoViewModel
import com.thatsmanmeet.taskyapp.room.deletedtodo.DeletedTodo
import com.thatsmanmeet.taskyapp.room.deletedtodo.DeletedTodoViewModel


@Composable
fun DeletedTodoItem(
    deletedTodo: DeletedTodo,
    todoViewModel: TodoViewModel,
    deletedTodoViewModel: DeletedTodoViewModel,
    modifier: Modifier = Modifier
) {
    val isShowing = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .heightIn(60.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                isShowing.value = true
            }
            .background(MaterialTheme.colorScheme.inverseOnSurface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = modifier.fillMaxWidth().padding(10.dp)){
            Text(
                text = deletedTodo.title!!,
                fontSize = 16.sp,
                textDecoration = if(deletedTodo.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
        }
    }
    if(isShowing.value){
        ActionDialogBox(
            isDialogShowing = isShowing,
            title = "Choose Action",
            message = "Do you want to restore or delete this task?",
            confirmButtonText = "Restore" ,
            dismissButtonText = "Delete",
            onConfirmClick = {
                todoViewModel.insertTodo(Todo(
                    ID = deletedTodo.ID,
                    title = deletedTodo.title,
                    todoDescription = deletedTodo.todoDescription,
                    isCompleted = deletedTodo.isCompleted,
                    date = deletedTodo.date,
                    time = deletedTodo.time,
                    notificationID = deletedTodo.notificationID,
                    isRecurring = deletedTodo.isRecurring
                ))
                deletedTodoViewModel.deleteDeletedTodo(deletedTodo)
            },
            onDismissClick = { 
                deletedTodoViewModel.deleteDeletedTodo(deletedTodo)
            },

        )
    }
}