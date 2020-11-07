package com.decadevs.accessmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.decadevs.accessmovies.ui.onboarding.OnboardingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainActivity_fragment_fl, OnboardingFragment())
                    .commit()
        }
    }
}