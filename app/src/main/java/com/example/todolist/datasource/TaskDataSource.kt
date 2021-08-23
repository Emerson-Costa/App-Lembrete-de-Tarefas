package com.example.todolist.datasource

import android.util.Log
import com.example.todolist.model.Task

object TaskDataSource{
    private val list = arrayListOf<Task>()

    fun getList() = list

    fun insertTask(task: Task){
        list.add(task.copy(id = list.size + 1))
        Log.e("TAG", "insertListeners ${TaskDataSource.getList()}")
    }
}