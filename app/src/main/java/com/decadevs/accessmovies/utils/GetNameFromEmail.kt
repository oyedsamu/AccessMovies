package com.decadevs.accessmovies.utils

class GetNameFromEmail() {
    fun getNameFrom(email: String): String {
        val positAt = email.indexOf('@')
        return email.substring(0, positAt)
    }
}