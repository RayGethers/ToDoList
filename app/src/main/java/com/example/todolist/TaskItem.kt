package com.example.todolist

import android.content.Context
import android.graphics.Color.green
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "task_item_table")
class TaskItem(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "desc") var desc: String,
    @ColumnInfo(name = "completed") var completed: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {
    @RequiresApi(Build.VERSION_CODES.O)
    private fun completedDate(): LocalDate? = if (completed == null) {
        null
    } else {
        LocalDate.parse(completed.toString(), dateFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isComplete() = completedDate() != null

    @RequiresApi(Build.VERSION_CODES.O)
    fun imageResource(): Int = if (isComplete()) R.drawable.check_circle_24 else R.drawable.unchecked_24

    @RequiresApi(Build.VERSION_CODES.O)
    fun imageColor(context: Context): Int = if(isComplete()) {
        green(context)
    } else {
        black(context)
    }

    private fun green(context: Context) = ContextCompat.getColor(context, R.color.green)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }
}