package com.decadevs.accessmovies

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decadevs.accessmovies.databinding.FragmentAddMovieBinding
import com.decadevs.accessmovies.validation.Validation


class AddMovieFragment : Fragment() {

    private var _binding : FragmentAddMovieBinding? = null
    private val binding get() = _binding!!



    private val btnChoose: Button? = null
    private  var btnUpload:Button? = null
    private val imageView: ImageView? = null
    private val filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)

        /** set navigation arrow from drawable **/
        binding.fragmentAddMovieToolbar.toolbarFragment.setNavigationIcon(R.drawable.ic_arrow_back_)


        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

           val checkUserInput = Validation(
               editTextTitle, editTextGenre, editTextRating, editTextCountry,
               editTextReleaseDate, editTextTicket, editTextDescription
           )

            if (checkUserInput != null)  {
                checkUserInput.error = "Field required"
            } else {
                findNavController().navigate(R.id.landingPage)
            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}