package com.decadevs.accessmovies.ui.login

import android.os.Bundle
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
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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

        binding.loginLoginBtn.setOnClickListener{
            val email = binding.loginEmailEt.text.toString()
            val password = binding.loginPasswordEt.text.toString()
            val validateEmail = Validator().validateEmail(email)
            val validatePassword = Validator().validatePassword(password)

            /** LOGIN USER */
            if(validateEmail && validatePassword) {
                view?.hideKeyboard()
                binding.loginEmailEt.text.clear()
                binding.loginPasswordEt.text.clear()
                binding.loginProgressBarPb.visibility = View.VISIBLE
                /** MAKE NETWORK CALL TO LOGIN USER */
            }

            /** INVALID EMAIL OR PASSWORD FORMAT */
            if(!validateEmail || !validatePassword) {
                view?.hideKeyboard()
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
}