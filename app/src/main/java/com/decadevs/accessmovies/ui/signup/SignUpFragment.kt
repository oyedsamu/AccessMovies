package com.decadevs.accessmovies.ui.signup

import android.R.attr
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decadevs.accessmovies.data.User
import com.decadevs.accessmovies.databinding.FragmentSignUpBinding
import com.decadevs.accessmovies.utils.Constants
import com.decadevs.accessmovies.utils.Validator
import com.decadevs.accessmovies.utils.hideKeyboard
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        view.setOnClickListener {
            it.hideKeyboard()
        }

        binding.signUpSignUpBtn.setOnClickListener {
            val validator = Validator()
            val username = binding.signUpNameEt.text.toString()
            val email = binding.signUpEmailEt.text.toString()
            val password = binding.signUpPasswordEt.text.toString()

            val validateUsername = validator.checkIfEmpty(username)
            val validateEmail = validator.validateEmail(email)
            val validatePassword = validator.validatePassword(password)

            if (!validateUsername && validateEmail && validatePassword) {
                view.hideKeyboard()
                binding.signUpNameEt.text.clear()
                binding.signUpEmailEt.text.clear()
                binding.signUpPasswordEt.text.clear()
                binding.signUpProgressBarPb.visibility = View.VISIBLE

                /** MAKE NETWORK CALL TO REGISTER NEW USER */
                registerNewUser(username, email, password)
            }

            if (validateUsername || !validateEmail || !validatePassword) {
                view.hideKeyboard()
                Snackbar.make(
                    requireView(),
                    "Please Input Valid Data To Continue",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        /** MOVE TO LOGIN SCREEN */
        binding.signUpLogInTv.setOnClickListener {
            Toast.makeText(this.context, "Implement Go To Sign In Screen", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun registerNewUser(username: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            requireActivity()
        ) { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show()
                val key = mAuth.currentUser?.uid
                val user = User(username, email)
                if (key != null) {
                    database.child("users").child(key).setValue(user)
                }
                // Make a call to firebase database and save the username and email address.
                // This will be called on Login and the username will be gotten as the name.
                val initialScreen = Constants.fragment
                if (initialScreen != null) {
                    findNavController().navigate(initialScreen)
                }
            } else {
                Toast.makeText(context, "Registration Failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}