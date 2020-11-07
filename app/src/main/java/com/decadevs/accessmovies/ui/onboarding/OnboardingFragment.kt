package com.decadevs.accessmovies.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.databinding.FragmentOnboardingBinding
import com.decadevs.accessmovies.utils.hideStatusBar
import com.google.android.material.tabs.TabLayoutMediator
import com.trapezoidlimited.groundforce.adapters.OnBoardingViewPagerAdapter

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        /** HIDE STATUS BAR */
        hideStatusBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** SET UP VIEWPAGER */
        val fragmentList: ArrayList<Fragment> = arrayListOf(
                OnboardingScreen1Fragment(),
                OnboardingScreen2Fragment(),
                OnboardingScreen3Fragment()
        )

        /** CONNECT FRAGMENTS TO ADAPTER */
        val adapter = activity?.supportFragmentManager?.let {
            OnBoardingViewPagerAdapter(fragmentList,it,lifecycle)
        }

        binding.onBoardingViewpagerVp.adapter = adapter
        val viewPager = activity?.findViewById<ViewPager2>(R.id.onBoarding_viewpager_vp)

        /** SET INDICATORS */
        TabLayoutMediator(
                binding.onBoardingIndicatorTl,
                binding.onBoardingViewpagerVp
        ) { tab, position ->
        }.attach()


        /** NAVIGATE THROUGH ON-BOARDING SCREENS */
        binding.onBoardingNextBtn.setOnClickListener {
            when(viewPager?.currentItem) {
                0 -> viewPager.currentItem = 1
                1 -> viewPager.currentItem = 2
                2 -> Toast.makeText(this.context, "end of pages", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}