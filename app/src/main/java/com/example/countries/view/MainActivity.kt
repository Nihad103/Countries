package com.example.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.countries.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("1000")
    }
}