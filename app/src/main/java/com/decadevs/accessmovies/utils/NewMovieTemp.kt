package com.decadevs.accessmovies.utils

import androidx.navigation.fragment.findNavController
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.viewmodel.NewMovieViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase


class NewMovieTemp() {

//    lateinit var newMovieViewModel: NewMovieViewModel
//    val database = FirebaseDatabase.getInstance().reference
//
//    newMoviewViewModel = ViewmodelProvider(this).get(NewMovieViewModel::class.java)
//
//    addMovie.setOnClickListener{
//        /** GET MOVIE DATA FROM FORM */
//        val name
//        val description
//        val releaseDate
//        val rating
//        val ticketPrice
//        val country
//        val genre
//        val photo
//        /** VALIDATE FORM */
//        val validator = Validator()
//        if(validator.checkIfEmpty(name) && validator.checkIfEmpty(description) && validator.checkIfEmpty(releaseDate) && validator.checkIfEmpty(rating))
//
//        /** SEND MOVIE DATA TO FIREBASE */
//        val movie = Movie(name, description, releaseDate, rating, ticketPrice, country, genre, photo)
//        newMovieViewModel.addNewMovie(movie)
//
//        /** OBSERVE REQUEST RESPONSE */
//        newMovieViewModel.newMovieResult.observe(viewlifecycleowner, observer{
//            val message = if(it == null) {
//                "Movie Successfully Added"
//            } else {
//                "Something Went Wrong. Movie Could Not Be Added."
//            }
//
//            Snackbar.make(this.context, message, Snackbar.LENGTH_LONG).show()
//
//            findNavController().navigate(R.id.landingPage)
//        })
//    }
}



