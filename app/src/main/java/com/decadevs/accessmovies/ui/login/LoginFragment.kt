package com.decadevs.accessmovies.ui.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.databinding.FragmentLoginBinding
import com.decadevs.accessmovies.databinding.FragmentOnboardingBinding
import com.decadevs.accessmovies.utils.Validator
import com.decadevs.accessmovies.utils.hideKeyboard
import com.decadevs.accessmovies.utils.hideStatusBar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var firebaseAuth: FirebaseAuth

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
        view.setOnClickListener{
            it.hideKeyboard()
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginLoginBtn.setOnClickListener{
            val validator = Validator()
            val email = binding.loginEmailEt.text.toString()
            val password = binding.loginPasswordEt.text.toString()
            val validateEmail = validator.validateEmail(email)
            val validatePassword = validator.validatePassword(password)

            /** LOGIN USER */
            if(validateEmail && validatePassword) {
                view.hideKeyboard()
                binding.loginEmailEt.text.clear()
                binding.loginPasswordEt.text.clear()
                binding.loginProgressBarPb.visibility = View.VISIBLE
                /** MAKE NETWORK CALL TO LOGIN USER */
                signin(email, password)
            }

            /** INVALID EMAIL OR PASSWORD FORMAT */
            if(!validateEmail || !validatePassword) {
                view.hideKeyboard()
                Snackbar.make(requireView(), "Please Enter A Valid Email And Password To Continue", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.loginRegisterTv.setOnClickListener {
            /** MOVE TO REGISTER SCREEN */
            Toast.makeText(this.context, "Implement Code To Move To Register Fragment", Toast.LENGTH_LONG).show()

        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun signin(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = firebaseAuth.currentUser
                    val name = user?.displayName
                    // Go to List and change Login button to Logout.
                    // Set name as name while launching the Fragment.
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Snackbar.make(requireView(), "Email or Password Incorrect", Snackbar.LENGTH_LONG).show()
                }

                // ...
            }
    }
}