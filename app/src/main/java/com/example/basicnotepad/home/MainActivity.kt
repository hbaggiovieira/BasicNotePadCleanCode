package com.example.basicnotepad.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.basicnotepad.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupButtons()

    }

    private fun setupButtons() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        toolbar.backButtonClick = { onBackPressed() }
        toolbar.rightButtonClick = { finish() }
    }
}