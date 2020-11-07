package com.decadevs.accessmovies.utils

class Validator {
    private fun checkIfEmpty(string: String): Boolean {
        return string.trim().isEmpty()
    }

    fun validateEmail(email: String):Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if(checkIfEmpty(email)) return false
        if(!email.matches(emailPattern)) return false
        return true
    }

    fun validatePassword(password: String): Boolean {
        if(checkIfEmpty(password)) return false
        if(password.length < 4) return false
        if(password.length < 4) return false
        return true
    }
}