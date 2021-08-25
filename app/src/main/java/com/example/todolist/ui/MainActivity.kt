package com.example.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*      Importando os elementos da activity para a classe Main,
           Lembrando que tem que instalar a dependencia do binding para funcionar.
         */
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Utilizar a raíz do binding
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter

        updateList()
        insertListeners()

    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this,AddTaskActivity::class.java),  CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this,AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()

    }

    private fun updateList() {
        val list = TaskDataSource.getList()
        if (list.isEmpty()) {
            binding.includeEmpty.emptyState.visibility = View.VISIBLE
        }else{
            binding.includeEmpty.emptyState.visibility = View.GONE
            binding.rvTasks.visibility = View.VISIBLE
        }

        adapter.submitList(list)
    }

    companion object{
        private const val CREATE_NEW_TASK = 1000
    }
}