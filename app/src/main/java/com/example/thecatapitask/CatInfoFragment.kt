package com.example.thecatapitask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import coil.api.load
import com.example.thecatapitask.databinding.FragmentCatInfoBinding
import com.example.thecatapitask.databinding.FragmentMainBinding

private const val CAT_URL = "url"
private const val CAT_ID = "id"

class CatInfoFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentCatInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(CAT_URL)
            param2 = it.getString(CAT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            imageCat.load(param1)
            textCatId.text = param2
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CatInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(CAT_URL, param1)
                    putString(CAT_ID, param2)
                }
            }
    }
}