package com.example.sayursegar.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshapp.ui.FreshListAdapter
import com.example.sayursegar.R
import com.example.sayursegar.application.FreshApp
import com.example.sayursegar.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val freshViewModel: FreshViewModel by viewModels {
        FreshViewModelFactory((applicationContext as FreshApp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val adapter = FreshListAdapter { fresh ->
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(fresh)
                findNavController().navigate(action)
            }
            binding.dataRecyclerView.adapter = adapter
            binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
            freshViewModel.allFresh.observe(viewLifecycleOwner) { freshs ->
                freshs.let {
                    if (freshs.isEmpty()) {
                        binding.emptytextView.visibility = View.VISIBLE
                        binding.illustrationimageView.visibility = View.VISIBLE
                    } else {
                        binding.emptytextView.visibility = View.GONE
                        binding.illustrationimageView.visibility = View.GONE
                    }
                    adapter.submitList(freshs)
                }
            }

        binding.addFAB.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}