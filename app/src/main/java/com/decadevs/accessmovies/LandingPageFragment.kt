package com.decadevs.accessmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.FragmentLandingPageBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LandingPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandingPageFragment : Fragment(R.layout.fragment_landing_page), MoviePhotoAdapter.OnItemClickListener {

//    private val viewModel by viewModels<LandingPageViewModel> ()

    private var _binding : FragmentLandingPageBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLandingPageBinding.bind(view)

        val adapter = MoviePhotoAdapter(this)




    }

    override fun onItemClick(movie: Movie) {

    }

}