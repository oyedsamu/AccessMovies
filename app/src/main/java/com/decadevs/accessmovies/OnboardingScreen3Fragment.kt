package com.decadevs.accessmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decadevs.accessmovies.databinding.FragmentOnboardingScreen2Binding
import com.decadevs.accessmovies.databinding.FragmentOnboardingScreen3Binding

class OnboardingScreen3Fragment : Fragment() {
    private var _binding: FragmentOnboardingScreen3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnboardingScreen3Binding.inflate(inflater, container, false)

        return binding.root
    }
}