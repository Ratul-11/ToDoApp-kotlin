package com.example.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTodoBinding
import android.graphics.Color

class ToDoAdapter(
    private val todos: MutableList<ToDo>
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    class ToDoViewHolder(val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ToDoViewHolder(binding)
    }

    fun addTodo(todo: ToDo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }
    fun deleteDoneTodos() {
        todos.removeAll { it.isChecked }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paint.isStrikeThruText = true
            tvTodoTitle.setTextColor(Color.parseColor("#FF6B6B"))
            tvTodoTitle.alpha = 0.5f
        } else {
            tvTodoTitle.paint.isStrikeThruText = false
            tvTodoTitle.setTextColor(Color.WHITE)
            tvTodoTitle.alpha = 1f
        }
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        var curToDo = todos[position]

        holder.binding.tvTodoTitle.text = curToDo.title

        holder.binding.cbDone.setOnCheckedChangeListener(null)

        holder.binding.cbDone.isChecked = curToDo.isChecked

        holder.binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
            curToDo.isChecked = isChecked
            toggleStrikeThrough(holder.binding.tvTodoTitle, isChecked)
        }
        toggleStrikeThrough(holder.binding.tvTodoTitle, curToDo.isChecked)
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}