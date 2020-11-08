package com.decadevs.accessmovies

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decadevs.accessmovies.adapters.MoviePhotoAdapter
import com.decadevs.accessmovies.adapters.OnItemClick
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.FragmentLandingPageBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LandingPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandingPageFragment : Fragment(R.layout.fragment_landing_page), OnItemClick {

//    private val viewModel by viewModels<LandingPageViewModel> ()

    val adapter = MoviePhotoAdapter(mutableListOf(), this)

    private var _binding : FragmentLandingPageBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLandingPageBinding.bind(view)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)




        binding.testing.setOnClickListener {
            findNavController().navigate(R.id.addMovieFragment)
        }


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

        var lists = mutableListOf<Movie>()
        for (i in 0..num){
            val movie = Movie(id= "aa", name= "From Russia With Love", description = "What love does", releaseDate = "2020", rating = "4", ticketPrice = "$88", country = "California", genre = "Action", photo = R.drawable.sixunderground)
          lists.add(movie)
        }

        return lists
    }

    
    override fun onItemClick(item: Movie, position: Int) {
        Log.d("CHECKING", "clicked")
        val CONSTANT_MOVIES_ID = "MoviesId"
        val bundle = bundleOf(CONSTANT_MOVIES_ID to item.id)
        findNavController().navigate(R.id.movieDetails)
    }


}