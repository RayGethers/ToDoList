package com.example.todolist

import kotlinx.coroutines.flow.Flow

interface TaskItemListener {

    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
    fun deleteTaskItem(taskItem: TaskItem)
}