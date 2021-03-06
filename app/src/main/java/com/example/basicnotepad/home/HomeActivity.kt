package com.example.basicnotepad.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.basicnotepad.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_home_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupToolbarButtons()
    }

    private fun setupToolbarButtons() {
        toolbar.backButtonClick = { onBackPressed() }
        toolbar.rightButtonClick = { finish() }
    }
}