package com.fyaman.todoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyaman.todoapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var toDoList : ArrayList<ToDo>
    private lateinit var toDoAdapter : ToDoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        toDoList = ArrayList<ToDo>()
        toDoAdapter = ToDoAdapter(toDoList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = toDoAdapter
        // GitHub Ogreniyorum

        try {
            val database = this.openOrCreateDatabase("ToDo", Context.MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM toDo",null)
            val taskIx = cursor.getColumnIndex("tasktitle")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()) {
                val taskTitle = cursor.getString(taskIx)
                val id = cursor.getInt(idIx)
                val task = ToDo(taskTitle,id)
                toDoList.add(task)
            }
            toDoAdapter.notifyDataSetChanged()
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val fabClicked = findViewById<FloatingActionButton>(R.id.fab)
        fabClicked.setOnClickListener(){
            val intent = Intent(this,DetailsActivity::class.java)
            startActivity(intent)
        }


    }
}