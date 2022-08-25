package com.fyaman.todoapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.fyaman.todoapp.databinding.ActivityDetailsBinding
import kotlinx.coroutines.selects.select
import java.lang.Exception


class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var database : SQLiteDatabase
    private var selectedId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setView()
    }

    fun setView() {
        database = this.openOrCreateDatabase("ToDo", Context.MODE_PRIVATE,null)
        val intent = intent
        val info = intent.getStringExtra("info")
        selectedId = intent.getStringExtra("id").toString()
        if (info.equals("edit")) {
            binding.saveButton.visibility = View.INVISIBLE
            binding.saveText.visibility = View.INVISIBLE
            binding.editButton.visibility = View.VISIBLE
            binding.editText.visibility = View.VISIBLE
            val cursor = database.rawQuery("SELECT * FROM toDo WHERE id = ?", arrayOf(selectedId.toString()))
            val taskTitleIx = cursor.getColumnIndex("tasktitle")
            val taskDescriptionIx = cursor.getColumnIndex("taskdescription")
            while (cursor.moveToNext()) {
                binding.taskTitle.setText(cursor.getString(taskTitleIx))
                binding.taskDes.setText(cursor.getString(taskDescriptionIx))
            }
            cursor.close()
            val intent = Intent(this,Task::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }else {
            binding.saveButton.visibility = View.VISIBLE
            binding.saveText.visibility = View.VISIBLE
            binding.editButton.visibility = View.INVISIBLE
            binding.editText.visibility = View.INVISIBLE
        }
    }

    fun editButton(view : View) {
        val taskTitle = binding.taskTitle.text.toString()
        val taskDescription = binding.taskDes.text.toString()
        try {
            database.execSQL("UPDATE toDo SET tasktitle = '$taskTitle' WHERE id ='$selectedId' ")
            database.execSQL("UPDATE toDo SET taskdescription = '$taskDescription' WHERE id ='$selectedId' ")

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    fun saveButton(view : View) {
        val taskTitle = binding.taskTitle.text.toString()
        val taskDescription = binding.taskDes.text.toString()
            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS toDo (id INTEGER PRIMARY KEY, tasktitle VARCHAR, taskdescription VARCHAR)")
                val sqlString = "INSERT INTO toDo (tasktitle, taskdescription) VALUES (?, ?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1, taskTitle)
                statement.bindString(2, taskDescription)
                statement.execute()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

}


