package com.example.thecatapitask

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecatapitask.adapter.CatAdapter
import com.example.thecatapitask.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "MainActivity"

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
            Log.d(TAG, "recyclerView init")
        }

        lifecycleScope.launchWhenCreated {
            catViewModel.getListData().collectLatest {
                itemAdapter.submitData(it)
                Log.d(TAG, "submit cats")
            }
        }
    }
}