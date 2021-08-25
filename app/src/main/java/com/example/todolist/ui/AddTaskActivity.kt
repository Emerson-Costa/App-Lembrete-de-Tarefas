package com.example.todolist.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.AcitivityAddTaskBinding
import com.example.todolist.datasource.TaskDataSource
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: AcitivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AcitivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.finById(taskId)?.let {
                binding.tilTitle.text = it.title
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
            }
        }

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

        binding.tilHour.editText?.setOnClickListener {
            val timerPicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timerPicker.addOnPositiveButtonClickListener {

                val minute = if (timerPicker.minute in 0..9) "0${timerPicker.minute}" else timerPicker.minute

                val hour: String
                if (timerPicker.hour in 0..9) {
                    hour = "0${timerPicker.hour}"
                } else {
                    hour = timerPicker.hour.toString()
                }

                binding.tilHour.text = "$hour:$minute"
            }
            timerPicker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener{
            finish()
        }

        binding.btnNewTask.setOnClickListener {

            val task = Task(
                title = binding.tilTitle.text ,
                date  = binding.tilDate.text,
                hour  = binding.tilHour.text,
                id    = intent.getIntExtra(TASK_ID,0)
            )

            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }


    }

    companion object{
        const val TASK_ID = "task_id"
    }
}