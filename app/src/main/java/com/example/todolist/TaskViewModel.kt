package com.example.todolist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.util.UUID

class TaskViewModel(private val repository: TaskItemRepository): ViewModel() {

    val taskItems: LiveData<List<TaskItem>> = repository.allTaskItem.asLiveData()

    fun addTaskItem(newTask: TaskItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertTaskItem(newTask)
    }

    fun updateTaskItem(taskItem: TaskItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateTaskItem(taskItem)
    }

    fun deleteTaskItem(taskItem: TaskItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteTaskItem(taskItem)
    }

    fun deleteAllTasks() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun getItemCount() = viewModelScope.launch(Dispatchers.IO) {
        repository.allTaskItem.count()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(taskItem: TaskItem) = CoroutineScope(Dispatchers.IO).launch {
        if (!taskItem.isComplete())
            taskItem.completed = TaskItem.dateFormatter.format(LocalDate.now())
        repository.updateTaskItem(taskItem)
    }
}

class TaskItemModelFactory(private val repository: TaskItemRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

        throw IllegalArgumentException("Unknown Error for ViewModel")
    }
}