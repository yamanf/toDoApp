package com.fyaman.todoapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fyaman.todoapp.databinding.ActivityTaskBinding
import java.lang.Exception
import android.content.Intent

class Task : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var database: SQLiteDatabase
    var selectedId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setView()
    }
    fun setView(){
        database = this.openOrCreateDatabase("ToDo", Context.MODE_PRIVATE, null)
        try {
            selectedId = intent.getIntExtra("id",1)
            val cursor = database.rawQuery("SELECT * FROM toDo WHERE id = ?", arrayOf(selectedId.toString()))
            val taskTitleIx = cursor.getColumnIndex("tasktitle")
            val taskDescriptionIx = cursor.getColumnIndex("taskdescription")
            while (cursor.moveToNext()) {
                binding.taskTitleShown.setText(cursor.getString(taskTitleIx))
                binding.description.setText(cursor.getString(taskDescriptionIx))
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.task_details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_delete -> {
                database.execSQL("DELETE FROM toDo WHERE id = '$selectedId'")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.action_edit -> {
                // class TaskHolder(val binding: ActivityTaskBinding): ViewHolder(binding.root){}
                //override fun onBindViewHolder(holder: TaskHolder) {
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("info", "edit")
                intent.putExtra("id", "$selectedId")
                println("$selectedId+task")
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun edit(view: View) {}
}

