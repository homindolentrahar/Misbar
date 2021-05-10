package com.homindolentrahar.misbar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        Disable action bar
        supportActionBar?.hide()
//        Change status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }
}