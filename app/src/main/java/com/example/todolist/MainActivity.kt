package com.example.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.View
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetchApi.setOnClickListener {
            getData()
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        todoAdapter = ToDoAdapter(mutableListOf())

        binding.rvTodoItems.layoutManager = LinearLayoutManager(this)
        binding.rvTodoItems.adapter = todoAdapter

        binding.AddbtnToDoList.setOnClickListener {
            val todoTitle = binding.etTodoList.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = ToDo(todoTitle)
                todoAdapter.addTodo(todo)
                binding.etTodoList.text.clear()
            }
        }

        binding.DeletebtnToDoList.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }

    private fun getData() {

        binding.progressBar.visibility = View.VISIBLE

        Retroftiinstance.apiInterface.getData().enqueue(object : Callback<ResponseDataClass> {

            override fun onResponse(
                call: Call<ResponseDataClass>,
                response: Response<ResponseDataClass>
            ) {
                binding.progressBar.visibility = View.GONE

                val data = response.body()

                if (data != null) {
                    // Add API data to your existing list
                    todoAdapter.addTodo(ToDo(data.title, data.completed))
                }
            }

            override fun onFailure(
                call: Call<ResponseDataClass>,
                t: Throwable
            ) {
                binding.progressBar.visibility = View.GONE

                Toast.makeText(
                    this@MainActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}