package com.fyaman.todoapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fyaman.todoapp.databinding.RecyclerRowBinding

class ToDoAdapter (val toDoList : ArrayList<ToDo>): RecyclerView.Adapter<ToDoAdapter.ToDoHolder>() {
    class ToDoHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ToDoHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        holder.binding.recyclerViewTextView.text = toDoList.get(position).title
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,Task::class.java)
            intent.putExtra("id",toDoList[position].id)
            holder.itemView.context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {
        return toDoList.size
    }
}