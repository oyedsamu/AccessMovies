package com.decadevs.accessmovies.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.databinding.FragmentOnboardingBinding
import com.decadevs.accessmovies.databinding.FragmentOnboardingScreen2Binding

class OnboardingScreen2Fragment : Fragment() {

    private var _binding: FragmentOnboardingScreen2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnboardingScreen2Binding.inflate(inflater, container, false)

        return binding.root
    }
}