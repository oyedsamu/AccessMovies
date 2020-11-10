package com.decadevs.accessmovies.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
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
import com.decadevs.accessmovies.utils.ConnectionType
import com.decadevs.accessmovies.utils.Constants
import com.decadevs.accessmovies.utils.hideKeyboard
import com.decadevs.accessmovies.utils.NetworkMonitorUtil
import com.decadevs.accessmovies.utils.showStatusBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LandingPageFragment : Fragment(R.layout.fragment_landing_page), OnItemClick {

    // network monitor variable
    private lateinit var networkMonitor: NetworkMonitorUtil

    //    private val adapter = MovieAdapter(mutableListOf(), this)
    var moviesDatabase = FirebaseDatabase.getInstance().getReference("Movies")
    var usersDatabase = FirebaseDatabase.getInstance().getReference("users")
    private lateinit var mAuth: FirebaseAuth

    private var _binding: FragmentLandingPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLandingPageBinding.bind(view)
        networkMonitor = NetworkMonitorUtil(requireActivity())

        // call network checker method
        checkForNetwork()

        /** SHOW STATUS BAR */
        showStatusBar()

        /** REAL TIME UPDATE OF MOVIES */
        moviesListener()

        /** SEARCH POSTS */
        binding.landingSearchSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })

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

        /** HIDE KEYBOARD */
        view.setOnClickListener {
            it.hideKeyboard()
        }
    }

    override fun onStart() {
        super.onStart()
        val name: String
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            name = currentUser.displayName.toString()
            Constants.name = name
            getUsername(currentUser.uid)

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


        var bundle = bundleOf(
            "id" to item.id,
            "title" to item.title,
            "movieDescription" to item.movieDescription,
            "releaseDate" to item.releaseDate,
            "rating" to item.rating,
            "ticketPrice" to item.ticketPrice,
            "country" to item.country,
            "genre" to item.genre,
            "image" to item.image
        )
        findNavController().navigate(R.id.movieDetails, bundle)
    }

    fun getUsername(key: String) {
        usersDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            var name: String = ""
            override fun onDataChange(snapshot: DataSnapshot) {
                name = snapshot.child(key).getValue(String::class.java).toString()
                Constants.name = name
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    /** LISTEN FOR MOVIES CHANGE */
    private fun moviesListener() {
        moviesDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val allMovies = arrayListOf<Movie>()

                for (snapshot in dataSnapshot.children) {
                    val id = snapshot.key.toString()
                    val name = snapshot.child("title").value.toString()
                    val description = snapshot.child("movieDescription").value.toString()
                    val releaseDate = snapshot.child("releaseDate").value.toString()
                    val rating = snapshot.child("rating").value.toString()
                    val ticketPrice = snapshot.child("ticketPrice").value.toString()
                    val country = snapshot.child("country").value.toString()
                    val genre = snapshot.child("genre").value.toString()
                    val photo = snapshot.child("image").value.toString()

                    allMovies.add(
                        Movie(
                            id,
                            name,
                            description,
                            releaseDate,
                            rating,
                            ticketPrice,
                            country,
                            genre,
                            photo
                        )
                    )
                }
                allMovies.reverse()
                /** UPDATE MOVIES RECYCLER VIEW */
                val recyclerView = binding.recyclerView
                adapter = MovieAdapter(allMovies.toMutableList(), this@LandingPageFragment)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(activity)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // ...
            }
        })
    }

    // check network call
    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    //
    private fun checkForNetwork() {
        networkMonitor.result = { isAvailable, type ->
            activity?.runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Wifi Connection")
                            }
                            ConnectionType.Cellular -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Cellular Connection")
                            }
                            else -> {
                            }
                        }
                    }
                    false -> {
                        Log.i("NETWORK_MONITOR_STATUS", "No Connection")
                    }
                }
            }
        }
    }
}