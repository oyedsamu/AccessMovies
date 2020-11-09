package com.decadevs.accessmovies.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf

import android.widget.Toast
import androidx.appcompat.app.AlertDialog


import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.adapters.MovieAdapter
import com.decadevs.accessmovies.adapters.OnItemClick
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.FragmentLandingPageBinding
import com.decadevs.accessmovies.utils.Constants
import com.decadevs.accessmovies.utils.showStatusBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


/**
 * A simple [Fragment] subclass.
 * Use the [LandingPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandingPageFragment : Fragment(R.layout.fragment_landing_page), OnItemClick {

//    private val viewModel by viewModels<LandingPageViewModel> ()

    private val adapter = MovieAdapter(mutableListOf(), this)
    var moviesDatabase = FirebaseDatabase.getInstance().getReference("Movies");
    private lateinit var mAuth: FirebaseAuth

    private var _binding: FragmentLandingPageBinding? = null
    private val binding get() = _binding!!

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            _binding = FragmentLandingPageBinding.bind(view)

            /** REAL TIME UPDATE OF MOVIES */
            moviesListener()

            /** SHOW STATUS BAR */
            showStatusBar()

            val recyclerView = binding.recyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
            mAuth = FirebaseAuth.getInstance()

            binding.landingSignInTv.setOnClickListener {
                Constants.fragment = R.id.landingPage
                findNavController().navigate(R.id.loginFragment)
            }

            binding.landingAddMovieImgBtn.setOnClickListener {
                findNavController().navigate(R.id.addMovieFragment)
            }


            binding.landingSignOutTv.setOnClickListener {
                signOutConfirm(it)
            }

        }

        override fun onStart() {
            super.onStart()
            val name: String
            val currentUser = mAuth.currentUser
            if (currentUser != null) {
                name = currentUser.displayName.toString()
                Constants.name = name
                binding.landingSignInTv.visibility = View.INVISIBLE
                binding.landingSignOutTv.visibility = View.VISIBLE
                binding.landingAddMovieImgBtn.visibility = View.VISIBLE
            }
        }

        fun signOutConfirm(view: View) {
            val builder = AlertDialog.Builder(view.context)
            builder.setTitle("SIGN OUT")

            //set content area
            builder.setMessage("Are you sure you want to Sign out of ACCESS MOVIES?")

            //set negative button
            builder.setPositiveButton(
                "SignOut"
            ) { dialog, id ->
                // User clicked Update Now button
                Toast.makeText(context, "Signing you out...", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.landingPage)

            }
            //set positive button
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, id ->
                dialog.dismiss()
            }
            builder.show()

        }

    override fun onItemClick(item: Movie, position: Int) {
        Log.d("CHECKING", "clicked")
        val CONSTANT_MOVIES_ID = "MoviesId"
        val bundle = bundleOf(CONSTANT_MOVIES_ID to item.id)
        findNavController().navigate(R.id.movieDetails)
    }

    /** LISTEN FOR MOVIES CHANGE */
    private fun moviesListener() {
        moviesDatabase.addValueEventListener(object : ValueEventListener {
            var allMovies = arrayListOf<Movie>()
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val id = snapshot.key.toString()
                    val name = snapshot.child("name").value.toString()
                    val description = snapshot.child("description").value.toString()
                    val releaseDate = snapshot.child("releaseDate").value.toString()
                    val rating = snapshot.child("rating").value.toString()
                    val ticketPrice = snapshot.child("ticketPrice").value.toString()
                    val country = snapshot.child("country").value.toString()
                    val genre = snapshot.child("genre").value.toString()
                    val photo = snapshot.child("photo").value.toString()

                    allMovies.add(Movie(id, name, description, releaseDate, rating, ticketPrice, country, genre, photo))
                }
                Log.d("allMovies", "$allMovies")
                /** UPDATE MOVIES RECYCLER VIEW */

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadComment:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}