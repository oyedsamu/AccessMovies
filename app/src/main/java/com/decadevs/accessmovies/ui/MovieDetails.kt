package com.decadevs.accessmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.decadevs.accessmovies.databinding.FragmentMoviedetailsBinding
import com.decadevs.accessmovies.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class MovieDetails : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private var _binding: FragmentMoviedetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        mAuth = FirebaseAuth.getInstance()
        val toolbar: Toolbar = binding.toolbar

//        if (activity is AppCompatActivity) {
//            (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        }

        (activity as AppCompatActivity).supportActionBar?.title = "New Title"
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
            binding.movieCommentSubmitButton.isEnabled = true


            // Implement Onclick listener for the button here
            binding.movieCommentSubmitButton.setOnClickListener {
                val comment = binding.movieCommentEt.text.toString()
                submitComment(name, comment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun submitComment(name: String, comment: String){
        // Implement the comment logic here for Firebase.
        // The comment is made per post.
    }
}