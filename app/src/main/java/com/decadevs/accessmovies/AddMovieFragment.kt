package com.decadevs.accessmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.FragmentAddMovieBinding
import com.decadevs.accessmovies.validation.Validation
import com.decadevs.accessmovies.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase


class AddMovieFragment : Fragment() {

    private var _binding : FragmentAddMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieViewModel: MovieViewModel
//    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)

        /** set navigation arrow from drawable **/
        binding.fragmentAddMovieToolbar.toolbarFragment.setNavigationIcon(R.drawable.ic_arrow_back_)


        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /** INITIALISE VIEWMODEL */
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // Fields required to fill
        val editTextTitle = binding.fragmentAddMovieTitleEt
        val editTextGenre = binding.fragmentAddMovieGenreEt
        val editTextRating = binding.fragmentAddMovieRatingsEt
        val editTextCountry = binding.fragmentAddMovieCountryEt
        val editTextReleaseDate = binding.fragmentAddMovieReleaseDateEt
        val editTextTicket = binding.fragmentAddMovieTicketPriceEt
        val editTextDescription = binding.fragmentAddMovieDescription




        // Validate user input
        binding.fragmentAddMovieAddImageBtn.setOnClickListener {

            /** ADD MOVIE TO DATABASE */
            //addMovie()

            val newMovie = Movie("1", "The End Of The World!", "Mooo Ha Ha Haaaaaaaa...",
                "Nov 2020", "5", "1000", "La La Land", "Apocalypse", 5)

            movieViewModel.addNewMovie(newMovie)

            Toast.makeText(this.context, "This works", Toast.LENGTH_SHORT).show()

//           val checkUserInput = Validation(editTextTitle, editTextGenre, editTextRating, editTextCountry,
//                   editTextReleaseDate, editTextTicket, editTextDescription )
//
//            if (checkUserInput != null)  {
//                checkUserInput.error = "Field required"
//            } else {

//                /** ADD MOVIE TO DATABASE */
//                addMovie()
//            }
        }
    }

    private fun addMovie() {

        /** ADD NEW MOVIE TO DATABASE */


//
//        /** OBSERVE RESPONSE */
//        movieViewModel.newMovieResult?.observe({ lifecycle }, {
//            if (it == null) {
//                /** NAVIGATE TO LANDING PAGE */
//                Toast.makeText(this.context, "Movie Successfully added!", Toast.LENGTH_SHORT).show()
////                Snackbar.make(this.context, "Movie Successfully added!", Snackbar.LENGTH_LONG).show()
//                findNavController().navigate(R.id.landingPage)
//            } else {
//                /** SHOW ERROR MESSAGE */
//                Toast.makeText(this.context, "Something went wrong. Movie could not be added.", Toast.LENGTH_SHORT).show()
////                Snackbar.make(this.context, "Something went wrong. Movie could not be added.", Snackbar.LENGTH_LONG).show()
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}