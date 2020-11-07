package com.decadevs.accessmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.decadevs.accessmovies.ui.signup.SignUpFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainActivity_fragment_fl, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}