package com.decadevs.accessmovies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decadevs.accessmovies.adapters.MoviePhotoAdapter
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.FragmentLandingPageBinding
import com.decadevs.accessmovies.utils.Constants
import com.google.firebase.auth.FirebaseAuth


class LandingPageFragment : Fragment(R.layout.fragment_landing_page),
    MoviePhotoAdapter.OnItemClickListener {

//    private val viewModel by viewModels<LandingPageViewModel> ()

    private lateinit var mAuth: FirebaseAuth
    val adapter = MoviePhotoAdapter(mutableListOf())
    private var _binding: FragmentLandingPageBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLandingPageBinding.bind(view)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        mAuth = FirebaseAuth.getInstance()

//        binding.landingSignInTv.setOnClickListener {
//            Constants.fragment = R.id.landingPage
//            findNavController().navigate(R.id.loginFragment)
//        }

//        binding.testing.setOnClickListener {
//            findNavController().navigate(R.id.addMovieFragment)
//        }
    }

    override fun onStart() {
        super.onStart()
        val name: String
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            name = currentUser.displayName.toString()
            Constants.name = name
//            binding.landingSignInTv.visibility = View.INVISIBLE
//            binding.landingSignOutTv.visibility = View.VISIBLE
//            binding.landingAddMovieImgBtn.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(movie: Movie) {

    }


    override fun onResume() {
        super.onResume()
        activity?.let {
            val gotten = getMovies(10)

            gotten.let {
                adapter.update(it)
            }
        }

    }


    fun getMovies(num: Int): MutableList<Movie> {
        val lists = mutableListOf<Movie>()
        for (i in 0..num) {
            val movie = Movie(
                id = "aa",
                name = "From Russia With Love",
                description = "What love does",
                releaseDate = "2020",
                rating = "4",
                ticketPrice = "$88",
                country = "California",
                genre = "Action",
                photo = R.drawable.sixunderground
            )
            lists.add(movie)
        }
        return lists
    }

}