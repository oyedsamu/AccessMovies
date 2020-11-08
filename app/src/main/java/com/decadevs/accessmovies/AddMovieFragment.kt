package com.decadevs.accessmovies

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decadevs.accessmovies.databinding.FragmentAddMovieBinding
import com.decadevs.accessmovies.validation.Validation
import java.util.*


class AddMovieFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding : FragmentAddMovieBinding? = null

    private val binding get() = _binding!!

    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    private lateinit var date : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)

        /** set navigation arrow from drawable **/
        binding.fragmentAddMovieToolbar.toolbarFragment.setNavigationIcon(R.drawable.ic_arrow_back_)



        /** Array adapter for spinner drop down for sex **/
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.rating,
                android.R.layout.simple_spinner_item
        ).also {ratingAdapter ->
            // Specify the layout to use when the list of choices appears
            ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.fragmentAddMovieRatingsEt.adapter = ratingAdapter


        }

        /** Array adapter for spinner drop down for sex **/
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.country,
                android.R.layout.simple_spinner_item
        ).also {countryAdapter ->
            // Specify the layout to use when the list of choices appears
            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.fragmentAddMovieRatingsEt.adapter = countryAdapter


        }

        return binding.root

    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fields required to fill
        val editTextTitle = binding.fragmentAddMovieTitleEt
        val editTextGenre = binding.fragmentAddMovieGenreEt
        val editTextCountry = binding.fragmentAddMovieCountryEt.selectedItem.toString() as EditText
        val editTextReleaseDate = binding.fragmentAddMovieReleaseDateEt
        val editTextTicket = binding.fragmentAddMovieTicketPriceEt
        val editTextDescription = binding.fragmentAddMovieDescription
        val editTextRating = binding.fragmentAddMovieRatingsEt.selectedItem.toString() as EditText






        // Validate user input
        binding.fragmentAddMovieAddImageBtn.setOnClickListener {

           val checkUserInput = Validation(editTextTitle, editTextGenre, editTextRating, editTextCountry,
                   editTextReleaseDate, editTextTicket, editTextDescription )



            val actionCb = binding.fragmentAddMovieGenreAction.isChecked
            val comedyCb = binding.fragmentAddMovieGenreComedy.isChecked
            val dramaCb = binding.fragmentAddMovieGenreDrama.isChecked
            val fantasyCb = binding.fragmentAddMovieGenreFantasy.isChecked
            val horrorCb = binding.fragmentAddMovieGenreHorror.isChecked
            val mysteryCb = binding.fragmentAddMovieGenreMystery.isChecked
            val romanceCb = binding.fragmentAddMovieGenreRomance.isChecked
            val thrillerCb = binding.fragmentAddMovieGenreThriller.isChecked

            var result = ""

            if (actionCb) {
                result += "Action "
            }

            if (comedyCb) {
                result += "Comedy "
            }

            if (dramaCb) {
                result += "Drama "
            }

            if (fantasyCb) {
                result += "Fantasy "
            }

            if (horrorCb) {
                result += "Horror "
            }

            if (mysteryCb) {
                result += "Mystery "
            }

            if (romanceCb) {
                result += "Romance "
            }

            if(thrillerCb) {
                result += "Thriller"
            }


            if (checkUserInput != null)  {
                checkUserInput.error = "Field required"

            } else if(result.isEmpty()) {
                Toast.makeText(requireContext(), "Click at least a genre", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.landingPage)
            }

        }





        val dateButton = binding.fragmentAddMovieReleaseDateEt

        /** Show the date button on click of date button **/
        dateButton.setOnClickListener {
            showDatePickerDialog(requireView())
        }

        /** Date set listener **/
        dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            date = "${month+1}/$day/$year"
            dateButton.setText(date)
        }

        }






    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.getItemAtPosition(position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
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
        val dialog = DatePickerDialog(requireContext(),
                android.R.style.ThemeOverlay_Material_Dialog_Alert,
                dateSetListener,year, month,day
        )
        dialog.show()

    }





}













