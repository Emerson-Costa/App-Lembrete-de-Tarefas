package com.example.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.AcitivityAddTaskBinding
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: AcitivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AcitivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

       insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
               val timeZone = TimeZone.getDefault()
               val offset   = timeZone.getOffset(Date().time) * -1
               binding.tilDate.text =  Date(it).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
    }


}