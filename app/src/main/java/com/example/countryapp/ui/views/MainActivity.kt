package com.example.countryapp.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.countryapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.beginTransaction().add(
            android.R.id.content,
            CountryListFragment.newInstance()
        ).commit()
    }
}