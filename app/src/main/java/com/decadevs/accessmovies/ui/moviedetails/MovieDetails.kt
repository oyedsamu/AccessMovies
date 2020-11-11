package com.decadevs.accessmovies.ui.moviedetails

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.adapters.CommentRecycler
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.FragmentMoviedetailsBinding
import com.decadevs.accessmovies.utils.Constants
import com.decadevs.accessmovies.utils.GetNameFromEmail
import com.decadevs.accessmovies.utils.hideKeyboard
import com.decadevs.accessmovies.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MovieDetails : Fragment() {
    /** A  */
    private lateinit var mAuth: FirebaseAuth
    private var _binding: FragmentMoviedetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieViewModel: MovieViewModel
    private var commentsDatabase = FirebaseDatabase.getInstance().getReference("Comments")
    lateinit var commentRecycler: RecyclerView
    lateinit var mAdapter: CommentRecycler
    lateinit var movieId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviedetailsBinding.inflate(inflater, container, false)
        view?.setOnClickListener {
            it.hideKeyboard()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** INITIALISE VIEWMODEL */
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        commentRecycler = binding.movieDetailsCommentRecyclerList
        commentRecycler.setHasFixedSize(true)
        commentRecycler.layoutManager = LinearLayoutManager(requireContext())

        /** GET BUNDLE ARGUMENTS */
        movieId = arguments?.getString("id").toString()

        val title = arguments?.getString("title")
        val movieDescription = arguments?.getString("movieDescription")
        var releaseDate = arguments?.getString("releaseDate")
        releaseDate = releaseDate!!.substring(releaseDate!!.length - 4)
        val rating = arguments?.getString("rating")
        val ticketPrice = arguments?.getString("ticketPrice")
        val country = arguments?.getString("country")
        val genre = arguments?.getString("genre")
        val image = arguments?.getString("image")

        /** POPULATE SCREEN WITH DETAILS */
        binding.movieRatings.text = rating
        binding.movieCountry.text = country
        binding.movieReleaseDate.text = releaseDate
        binding.movieGenres.text = genre
        binding.movieDescription.text = movieDescription
        binding.ticketPrice.text = ticketPrice
        Glide.with(this)
            .load(image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.sixunderground)
            .into(binding.movieImg)

        mAuth = FirebaseAuth.getInstance()
        val toolbar: Toolbar = binding.toolbar

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }

        (activity as AppCompatActivity).supportActionBar?.title = title.toString()

        /** REAL TIME UPDATE OF COMMENTS */
        commentsListener()
        view.hideKeyboard()
    }

    /** Checks if the user is logged in */
    override fun onStart() {
        super.onStart()
        val name: String
        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            name = Constants.name.toString()
            binding.movieCommentSubmitButton.isEnabled = true

            // Implement Onclick listener for the button here
            binding.movieCommentSubmitButton.setOnClickListener {
                val comment = binding.movieCommentEt.text.toString()

                if (movieId != null) {
                    addComment(name, comment, movieId)
                }
            }
        } else {
            /** MAKE COMMENT BUTTON TEXT TO LOGIN */
            binding.movieCommentSubmitButton.text = "Login"
            /** MAKE COMMENT BUTTON LOG IN USER */
            binding.movieCommentSubmitButton.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }
            /** ADD HINT TO COMMENT BOX TO TELL USER TO LOGIN */
            binding.movieCommentEt.hint = "Log Into Your Account To Add Comments."
            /** DISABLE COMMENT BOX */
            binding.movieCommentEt.isFocusable = false
        }
    }

    /** Prevents memory leaks by setting the binding to null at the end of lifecycle */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** Function for Comments to be added */
    fun addComment(name: String, comment: String, movieId: String) {
        /** ADD NEW COMMENT TO DATABASE */

        if(name.isNotEmpty() && comment.isNotEmpty() && movieId.isNotEmpty()){
            val comment = Comment("1", movieId, name, comment)
            movieViewModel.addNewComment(comment)
        } else {
            Toast.makeText(this.context, "Please enter a comment", Toast.LENGTH_LONG).show()
        }
    }

    /** LISTEN FOR COMMENT CHANGE */
    private fun commentsListener() {
        commentsDatabase.addValueEventListener(object : ValueEventListener {
            var allComments = arrayListOf<Comment>()
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                allComments.clear()
                for (snapshot in dataSnapshot.children) {
                    val username = snapshot.child("user").value.toString()
                    val comment = snapshot.child("comment").value.toString()
                    val movieId = snapshot.child("movieId").value.toString()
                    allComments.add(Comment("1", movieId, username, comment))
                }

                val movieComments = arrayListOf<Comment>()
                /** GET COMMENTS FOR CURRENT MOVIE */
                for (comment in allComments) {
                    if (comment.movieId == movieId) {
                        movieComments.add(comment)
                    }
                }
                /** UPDATE COMMENTS RECYCLER VIEW */
                mAdapter = CommentRecycler(movieComments)
                mAdapter.notifyDataSetChanged()
                commentRecycler.adapter = mAdapter

                binding.movieCommentEt.text.clear()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        })
    }
}