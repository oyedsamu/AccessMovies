package com.decadevs.accessmovies.ui.moviedetails

import android.content.ContentValues
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
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.databinding.FragmentMoviedetailsBinding
import com.decadevs.accessmovies.utils.Constants
import com.decadevs.accessmovies.utils.GetNameFromEmail
import com.decadevs.accessmovies.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MovieDetails : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private var _binding: FragmentMoviedetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieViewModel: MovieViewModel
    var commentsDatabase = FirebaseDatabase.getInstance().getReference("Comments");

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviedetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** INITIALISE VIEWMODEL */
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        mAuth = FirebaseAuth.getInstance()
        val toolbar: Toolbar = binding.toolbar

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }

        (activity as AppCompatActivity).supportActionBar?.title = "New Title"

        /** REAL TIME UPDATE OF COMMENTS */
        commentsListener()
    }

    override fun onStart() {
        super.onStart()
        val name: String
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            name = GetNameFromEmail().getNameFrom(currentUser.email.toString())
            Constants.name = name
//            binding.landingSignInTv.visibility = View.INVISIBLE
//            binding.landingSignOutTv.visibility = View.VISIBLE
//            binding.landingAddMovieImgBtn.visibility = View.VISIBLE
            binding.movieCommentSubmitButton.isEnabled = true

            // Implement Onclick listener for the button here
            binding.movieCommentSubmitButton.setOnClickListener {
                val comment = binding.movieCommentEt.text.toString()
                addComment(name, comment)
            }
        } else {
            /** MAKE COMMENT BUTTON TEXT TO LOGIN */
            /** MAKE COMMENT BUTTON LOG IN USER */
            /** ADD HINT TO COMMENT BOX TO TELL USER TO LOGIN */
            /** DISABLE COMMENT BOX */
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addComment(name: String, comment: String) {
        /** ADD NEW COMMENT TO DATABASE */
        val comment = Comment("1", "3", name, "i love movies")
        movieViewModel.addNewComment(comment)

        /** OBSERVE RESPONSE */
        movieViewModel.newMovieResult?.observe(viewLifecycleOwner, Observer{
            if (it == null) {
                Snackbar.make(requireView(), "Contact Succssfully added", Snackbar.LENGTH_LONG).show()
//                Toast.makeText(this.context, "Comment Successfully added!", Toast.LENGTH_SHORT).show()
            } else {
                Snackbar.make(requireView(), "Something went wrong. Comment could not be added.", Snackbar.LENGTH_LONG).show()
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
                /** UPDATE COMMENTS RECYCLER VIEW */

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadComment:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}