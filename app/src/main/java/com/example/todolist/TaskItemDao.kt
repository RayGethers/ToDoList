package com.example.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskItemDao {
    @Query("SELECT * FROM task_item_table ORDER BY id ASC")
    fun allTaskItems(): Flow<List<TaskItem>>

    @Query("DELETE FROM task_item_table")
    fun deleteAllTasks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskItem(taskItem: TaskItem)

    @Update
    fun updateTaskItem(taskItem: TaskItem)

    @Delete
    fun deleteTaskItem(taskItem: TaskItem)

}