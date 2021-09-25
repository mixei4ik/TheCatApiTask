package com.example.thecatapitask

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecatapitask.adapter.CatAdapter
import com.example.thecatapitask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val itemAdapter = CatAdapter()
    private lateinit var binding: ActivityMainBinding

    private val catViewModel by viewModels<CatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        catViewModel.items.observe(this, Observer {
            it ?: return@Observer
            itemAdapter.addItems(it)
        })
    }
}