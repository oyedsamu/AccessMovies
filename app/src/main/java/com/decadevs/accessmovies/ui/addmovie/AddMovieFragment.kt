package com.decadevs.accessmovies.ui.addmovie

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.FragmentAddMovieBinding
import com.decadevs.accessmovies.utils.showStatusBar
import com.decadevs.accessmovies.validation.Validation
import com.decadevs.accessmovies.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class AddMovieFragment : Fragment() {
    /**
     * All needed variables
     */
    private var _binding: FragmentAddMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var date: String
    private lateinit var mStorageRef: StorageReference
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieCountry: String
    private lateinit var movieRating: String
    private lateinit var movieImageUrl: String
    private lateinit var imageUri: Uri


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)
        val countries = resources.getStringArray(R.array.country)
        val ratings = resources.getStringArray(R.array.rating)
        movieImageUrl = ""

        showStatusBar()
        /** set navigation arrow from drawable **/
        binding.fragmentAddMovieToolbar.toolbarFragment.setNavigationIcon(R.drawable.ic_arrow_back_)


        /** Array adapter for spinner drop down for rating **/
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.rating,
            android.R.layout.simple_spinner_item
        ).also { ratingAdapter ->
            // Specify the layout to use when the list of choices appears
            ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.fragmentAddMovieCountryEt.adapter = ratingAdapter

            binding.fragmentAddMovieCountryEt.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    movieRating = ratings[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        /** Array adapter for spinner drop down for country **/
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.country,
            android.R.layout.simple_spinner_item
        ).also { countryAdapter ->
            // Specify the layout to use when the list of choices appears
            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.fragmentAddMovieRatingsEt.adapter = countryAdapter

            binding.fragmentAddMovieRatingsEt.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    movieCountry = countries[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        binding.fragmentAddMovieUpPhotoBtn.setOnClickListener {
            checkRunTimePermission()
        }
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /** INITIALISE VIEWMODEL */
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)


        // Fields required to fill
        val editTextTitle = binding.fragmentAddMovieTitleEt
        val editTextReleaseDate = binding.fragmentAddMovieReleaseDateEt
        val editTextTicket = binding.fragmentAddMovieTicketPriceEt
        val editTextDescription = binding.fragmentAddMovieDescription

        mStorageRef = FirebaseStorage.getInstance().reference


        /** User should be able to go back to previous screen */
        binding.fragmentAddMovieToolbar.toolbarFragment.setOnClickListener {
            findNavController().popBackStack()
        }

        /** Validate user inputs using the editable fields */
        binding.fragmentAddMovieAddImageBtn.setOnClickListener {
            val checkUserInput = Validation(
                editTextTitle,
                editTextReleaseDate,
                editTextTicket,
                editTextDescription
            )

            val actionCb = binding.fragmentAddMovieGenreAction.isChecked
            val comedyCb = binding.fragmentAddMovieGenreComedy.isChecked
            val dramaCb = binding.fragmentAddMovieGenreDrama.isChecked
            val fantasyCb = binding.fragmentAddMovieGenreFantasy.isChecked
            val horrorCb = binding.fragmentAddMovieGenreHorror.isChecked
            val mysteryCb = binding.fragmentAddMovieGenreMystery.isChecked
            val romanceCb = binding.fragmentAddMovieGenreRomance.isChecked
            val thrillerCb = binding.fragmentAddMovieGenreThriller.isChecked

            var result = ""

            if (actionCb) result += "Action|"
            if (comedyCb) result += "Comedy|"
            if (dramaCb) result += "Drama|"
            if (fantasyCb) result += "Fantasy|"
            if (horrorCb) result += "Horror"
            if (mysteryCb) result += "Mystery|"
            if (romanceCb) result += "Romance|"
            if (thrillerCb) result += "Thriller|"

            when {
                checkUserInput != null -> {
                    checkUserInput.error = "Field required"
                }
                result.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Click at least a genre",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                else -> {
                    val movieGenres =
                        if (result.isNotEmpty()) result.substring(0, result.length - 1) else result
                    val movieTitle = editTextTitle.text.toString()
                    val movieReleaseDate = editTextReleaseDate.text.toString()
                    val movieTicket = editTextTicket.text.toString()
                    val movieDescription = editTextDescription.text.toString()

                    val movie = Movie(
                        "1",
                        movieTitle,
                        movieDescription,
                        movieReleaseDate,
                        movieRating,
                        movieTicket,
                        movieCountry,
                        movieGenres,
                        movieImageUrl
                    )
                    /** ADD MOVIE TO DATABASE */
                    addMovie(movie)
                    findNavController().navigate(R.id.landingPage)
                }
            }
        }
        /** Show the date button on click of date button **/
        editTextReleaseDate.setOnClickListener {
            showDatePickerDialog(requireView())
        }

        /** Date set listener **/
        dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                date = "${month + 1}/$day/$year"
                editTextReleaseDate.setText(date)
            }
    }

    /** Show Date picker Dialog Function **/
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDatePickerDialog(v: View) {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        /** Date dialog picker style **/
        val dialog = DatePickerDialog(
            requireContext(),
            android.R.style.ThemeOverlay_Material_Dialog_Alert,
            dateSetListener, year, month, day
        )
        dialog.show()
    }

    private fun checkRunTimePermission() {
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromGallery()
            }
        } else {
            //system OS is < Marshmallow
            pickImageFromGallery()
        }
    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;

        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    /** Handle requested permission result */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    /** Handle Image Request gracefully */
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    /**handle result of picked image */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data!!
            val myImage = binding.fragmentAddMovieUserMovie

            val progress = ProgressDialog(context)
            progress.setTitle("Uploading your movie details...")
            progress.show()
            val imageRef = mStorageRef.child("imageFolder/${imageUri.lastPathSegment}")
            val uploadTask = imageRef.putFile(imageUri)
            uploadTask.addOnSuccessListener {
                progress.dismiss()
                Toast.makeText(this.context, "Uploaded", Toast.LENGTH_LONG).show()
                imageRef.downloadUrl.addOnSuccessListener {
                    movieImageUrl = it.toString()
                }
            }
            myImage.setImageURI(imageUri)
        }
    }

    private fun addMovie(movie: Movie) {
        /** ADD NEW MOVIE TO DATABASE */
        movieViewModel.addNewMovie(movie)
        Snackbar.make(requireView(), "Movie Successfully Added", Snackbar.LENGTH_LONG).show()

        /** OBSERVE RESPONSE */
        movieViewModel.newMovieResult?.observe({ lifecycle }, {
            if (it == null) {
                /** NAVIGATE TO LANDING PAGE */
                Toast.makeText(this.context, "Movie Successfully added!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.landingPage)
            } else {
                /** SHOW ERROR MESSAGE */
                Toast.makeText(
                    this.context,
                    "Something went wrong. Movie could not be added.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}













