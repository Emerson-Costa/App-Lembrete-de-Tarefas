package com.example.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        insertListeners()
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this,AddTaskActivity::class.java),  CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            Log.e("TAG", "listerner: $it")
        }

        adapter.listenerDelete = {
            Log.e("TAG", "listerner: $it")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK){
            binding.rvTasks.adapter = adapter
            adapter.submitList(TaskDataSource.getList())
        }
    }

    companion object{
        private const val CREATE_NEW_TASK = 1000
    }
}