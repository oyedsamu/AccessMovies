package com.decadevs.accessmovies.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.databinding.FragmentLoginBinding
import com.decadevs.accessmovies.utils.Constants
import com.decadevs.accessmovies.utils.GetNameFromEmail
import com.decadevs.accessmovies.utils.Validator
import com.decadevs.accessmovies.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** HIDE KEYBOARD */
        view.setOnClickListener {
            it.hideKeyboard()
        }

        database = FirebaseDatabase.getInstance().getReference("users")
        firebaseAuth = FirebaseAuth.getInstance()


        binding.loginSignUpTv.setOnClickListener {
            /** MOVE TO REGISTER SCREEN */
            findNavController().navigate(R.id.signUpFragment)
        }

        binding.logInLogInBtn.setOnClickListener {
            val validator = Validator()
            binding.logInLogInBtn.setOnClickListener {
                val email = binding.loginEmailEt.text.toString()
                val password = binding.loginPasswordEt.text.toString()
                val validateEmail = validator.validateEmail(email)
                val validatePassword = validator.validatePassword(password)

                /** LOGIN USER */
                if (validateEmail && validatePassword) {
                    view.hideKeyboard()
                    binding.loginEmailEt.text.clear()
                    binding.loginPasswordEt.text.clear()
                    binding.logInProgressBarPb.visibility = View.VISIBLE
                    /** MAKE NETWORK CALL TO LOGIN USER */
                    signin(email, password)
                }

                /** INVALID EMAIL OR PASSWORD FORMAT */
                if (!validateEmail || !validatePassword) {
                    view.hideKeyboard()
                    Snackbar.make(
                        requireView(),
                        "Please Enter A Valid Email And Password To Continue",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun signin(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth.currentUser
                    val name = GetNameFromEmail().getNameFrom(user?.email.toString())

                    Constants.name = name
                    Snackbar.make(
                        requireView(),
                        "$name logged in successfully",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Constants.fragment?.let { findNavController().navigate(it) }
                } else {
                    // If sign in fails, display a message to the user.
                    Snackbar.make(
                        requireView(),
                        "Email or Password Incorrect",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }
}
