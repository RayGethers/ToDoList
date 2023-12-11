package com.example.todolist

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow

class MainActivity : AppCompatActivity(), TaskItemListener {
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as ToDoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "addTaskTag")
        }
        binding.clearAllTasksButton.setOnClickListener {
            deleteAllTaskItems()
        }
        setRecyclerView()
    }
    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this) {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    override fun deleteTaskItem(taskItem: TaskItem) {
        taskViewModel.deleteTaskItem(taskItem)
    }

    private fun deleteAllTaskItems() {
        val confirm = AlertDialog.Builder(this)
        confirm.setPositiveButton("Yes") {_, _ ->
            taskViewModel.deleteAllTasks()
            Toast.makeText(
                this,
                "Successfully removed all tasks",
                Toast.LENGTH_SHORT).show()
        }
        confirm.setNegativeButton("No") { _, _ -> }
        confirm.setTitle("Delete All???")
        confirm.setMessage("Are you sure you want to delete all tasks?")
        confirm.create().show()
    }


}