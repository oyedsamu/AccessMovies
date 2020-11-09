package com.decadevs.accessmovies.validation

import android.widget.EditText

class Validation {

    companion object {

        /**Vararg to take inputs**/
        operator fun invoke(vararg allEdits: EditText): EditText? {

            for (view in allEdits) {
                if (view.text.isEmpty()) {
                    return view
                }
            }
            return null
        }
    }
}