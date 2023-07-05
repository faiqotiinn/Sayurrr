package com.example.sayursegar.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sayursegar.R
import com.example.sayursegar.application.FreshApp
import com.example.sayursegar.databinding.FragmentSecondBinding
import com.example.sayursegar.model.Fresh

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val freshViewModel: FreshViewModel by viewModels {
        FreshViewModelFactory((applicationContext as FreshApp).repository)
    }

    private val args : SecondFragmentArgs by navArgs()
    private var fresh: Fresh? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fresh = args.fresh

        if (fresh != null) {
            binding.deletebutton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameeditText.setText(fresh?.name)
            binding.addreseditText.setText(fresh?.address)
            binding.typeEditText.setText(fresh?.type)
        }

        val name = binding.nameeditText.text
        val address = binding.addreseditText.text
        val type = binding.typeEditText.text
        binding.saveButton.setOnClickListener {

            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()) {
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (type.isEmpty()) {
                Toast.makeText(context, "Jenis tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }else{
                if (fresh == null) {
                    val fresh = Fresh(0, name.toString(), address.toString(), type.toString())
                    freshViewModel.insert(fresh)
                }else{
                    val fresh = Fresh(fresh?.id!!, name.toString(), address.toString(), type.toString())
                    freshViewModel.update(fresh)
                }
                findNavController().popBackStack()
            }
        }

        binding.deletebutton.setOnClickListener {
            fresh?.let {freshViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}