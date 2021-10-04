package com.example.thecatapitask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapitask.adapter.CatAdapter
import com.example.thecatapitask.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val catViewModel by viewModels<CatViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val itemAdapter = CatAdapter(object : Navigator {
            override fun openCatInfoFragment(param1: String, param2: String) {
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out
                    )
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, CatInfoFragment.newInstance(param1, param2))
                    .commit()
            }
        })

        itemAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        initRecyclerView(itemAdapter)
        initRecyclerAdapter(itemAdapter)

        return binding.root
    }

    private fun initRecyclerAdapter(itemAdapter: CatAdapter) {
        lifecycleScope.launchWhenCreated {
            catViewModel.catModel.collectLatest {
                itemAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView(itemAdapter: CatAdapter) {
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
