package com.decadevs.accessmovies.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.decadevs.accessmovies.R

/**
 * show status bar
 */
fun Fragment.showStatusBar() {
    requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//    activity?.window?.statusBarColor = Color.WHITE;
    requireActivity().window.statusBarColor = resources.getColor(R.color.orange)
}

/**
 * hide status bar
 */

fun Fragment.hideStatusBar(){
    // Hide the status bar.
    activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    activity?.actionBar?.hide()
}