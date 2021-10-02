package com.example.thecatapitask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecatapitask.adapter.CatAdapter
import com.example.thecatapitask.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest

class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val itemAdapter = CatAdapter(object : Navigator {
        override fun openCatInfoFragment(param1: String, param2: String) {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, CatInfoFragment.newInstance(param1, param2))
                .commit()
        }

    })

    private val catViewModel by viewModels<CatViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        initRecyclerView()
        initRecyclerAdapter()

        return binding.root
    }

    private fun initRecyclerAdapter() {
        lifecycleScope.launchWhenCreated {
            catViewModel.getListData().collectLatest {
                itemAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}