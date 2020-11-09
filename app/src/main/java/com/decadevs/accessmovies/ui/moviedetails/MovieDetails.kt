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
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.adapters.CommentRecycler
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.FragmentMoviedetailsBinding
import com.decadevs.accessmovies.utils.Constants
import com.decadevs.accessmovies.utils.GetNameFromEmail
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
    private var commentsDatabase = FirebaseDatabase.getInstance().getReference("Comments");
    var moviesDatabase = FirebaseDatabase.getInstance().getReference("Movies");
    lateinit var commentRecycler: RecyclerView
    lateinit var mAdapter: CommentRecycler

    val movieId = Constants.movieId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviedetailsBinding.inflate(inflater, container, false)

//        Toast.makeText(this.context, "$movieId", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** INITIALISE VIEWMODEL */

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        commentRecycler = binding.movieDetailsCommentRecyclerList
        commentRecycler.setHasFixedSize(true)
        commentRecycler.layoutManager = LinearLayoutManager(requireContext())


        /** Set the Color of the bar to be inferred from the main image */
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.sixunderground)

        Palette.from(bitmap).generate(Palette.PaletteAsyncListener() {
            if (it != null) {
                binding.fragmentMovieDetailsCollapsingToolBar.setContentScrimColor(
                    it.getMutedColor(
                        R.attr.colorPrimary
                    )
                )
            }
        })
        mAuth = FirebaseAuth.getInstance()
        val toolbar: Toolbar = binding.toolbar

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }

        (activity as AppCompatActivity).supportActionBar?.title = "New Title"

        /** REAL TIME UPDATE OF COMMENTS */
        commentsListener()
        /** FILL DETAILS SCREEN */
        moviesListener()
    }

    /** Checks if the user is logged in */
    override fun onStart() {
        super.onStart()
        val name: String
        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            name = GetNameFromEmail().getNameFrom(currentUser.email.toString())
            Constants.name = name
            binding.movieCommentSubmitButton.isEnabled = true

            // Implement Onclick listener for the button here
            binding.movieCommentSubmitButton.setOnClickListener {
                val comment = binding.movieCommentEt.text.toString()
//                addComment(name, comment, movieId)
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
        val comment = Comment("1", movieId, name, comment)
        movieViewModel.addNewComment(comment)

        /** OBSERVE RESPONSE */
        movieViewModel.newMovieResult?.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                Snackbar.make(requireView(), "Contact Succssfully added", Snackbar.LENGTH_LONG)
                    .show()
//                Toast.makeText(this.context, "Comment Successfully added!", Toast.LENGTH_SHORT).show()
            } else {
                Snackbar.make(
                    requireView(),
                    "Something went wrong. Comment could not be added.",
                    Snackbar.LENGTH_LONG
                ).show()
//                Toast.makeText(this.context, "Something went wrong. Comment could not be added.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getComments() {
        /** CALL VIEWMODEL TO GET COMMENTS */
        movieViewModel.getAllComments()
    }

    fun getMovie() {
        /** CALL VIEWMODEL TO GET MOVIE DETAILS */
    }

    /** LISTEN FOR COMMENT CHANGE */
    private fun commentsListener() {
        commentsDatabase.addValueEventListener(object : ValueEventListener {
            var allComments = arrayListOf<Comment>()
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val username = snapshot.child("user").value.toString()
                    val comment = snapshot.child("comment").value.toString()
                    val movieId = snapshot.child("movieId").value.toString()
                    allComments.add(Comment("1", movieId, username, comment))
                }
                Log.d("allData", "$allComments")
                val movieComments = arrayListOf<Comment>()
                /** GET COMMENTS FOR CURRENT MOVIE */
                for (comment in allComments) {
                    if (comment.movieId == movieId) {
                        movieComments.add(comment)
                    }
                }
                /** UPDATE COMMENTS RECYCLER VIEW */
                movieComments.reverse()
                mAdapter = CommentRecycler(this@MovieDetails.requireContext(), movieComments)
                mAdapter.notifyDataSetChanged()
                commentRecycler.adapter = mAdapter

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadComment:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    /** LISTEN FOR MOVIE CHANGE */
    private fun moviesListener() {
        moviesDatabase.addValueEventListener(object : ValueEventListener {
            var allMovies = arrayListOf<Movie>()
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val title = snapshot.child("title").value.toString()
                    val movieDescription = snapshot.child("movieDescription").value.toString()
                    val releaseDate = snapshot.child("releaseDate").value.toString()
                    val rating = snapshot.child("rating").value.toString()
                    val ticketPrice = snapshot.child("ticketPrice").value.toString()
                    val country = snapshot.child("country").value.toString()
                    val genre = snapshot.child("genre").value.toString()
                    val image = snapshot.child("image").value.toString()

                    allMovies.add(Movie("1", title, movieDescription, releaseDate, rating, ticketPrice, country, genre, image))
                }
                Log.d("allMovies", "$allMovies")
                val movieComments = arrayListOf<Comment>()
                /** GET DETAILS FOR CURRENT MOVIE */
                for(movie in allMovies) {
                    if(movie.id == movieId) {
                        setMovieDetails(movie)
                    }
                }
                /** UPDATE COMMENTS RECYCLER VIEW */
                movieComments.reverse()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadComment:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    fun setMovieDetails(movie: Movie) {
        binding.movieRatings.text = movie.rating
        binding.movieCountry.text = movie.country
        binding.movieReleaseDate.text = movie.releaseDate
        binding.movieGenres.text = movie.genre
        binding.movieDescription.text = movie.movieDescription
    }
}