package com.decadevs.accessmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.decadevs.accessmovies.adapters.MoviePhotoAdapter
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.FragmentLandingPageBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LandingPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandingPageFragment : Fragment(R.layout.fragment_landing_page), MoviePhotoAdapter.OnItemClickListener {

//    private val viewModel by viewModels<LandingPageViewModel> ()

    val adapter = MoviePhotoAdapter(mutableListOf())

    private var _binding : FragmentLandingPageBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLandingPageBinding.bind(view)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)




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

        var lists = mutableListOf<Movie>()
        for (i in 0..num){
            val movie = Movie(id= "aa", name= "seen", description = "gthe", releaseDate = "asas", rating = "gge", ticketPrice = "gg", country = "thaa", genre = "asas", photo = R.drawable.ic_baseline_add_business)
          lists.add(movie)
        }


        return lists
    }

}