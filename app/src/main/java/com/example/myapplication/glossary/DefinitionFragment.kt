package com.example.myapplication.glossary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDefinitionBinding
import com.example.myapplication.databinding.FragmentGameWonBinding
import com.example.myapplication.databinding.FragmentSubchapterListBinding
import com.example.myapplication.quiz.GameOverFragmentDirections
import com.example.myapplication.quiz.GameWonFragmentArgs
import com.example.myapplication.utilities.InjectorUtils

class DefinitionFragment : Fragment() {
    private val args: DefinitionFragmentArgs by navArgs()

    private val viewModel: DefinitionViewModel by viewModels {
        InjectorUtils.provideDefinitionViewModelFactory(this, args.wordId)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDefinitionBinding.inflate(inflater, container, false)
            .apply {
                sviewModel = viewModel
//                lifecycleOwner = viewLifecycleOwner
//                toolbar.setNavigationOnClickListener { view ->
//                    view.findNavController().navigateUp()
//                }

            }
        binding.setLifecycleOwner(this);
//        binding.textView4.setText("hi")
        return binding.root
    }
}