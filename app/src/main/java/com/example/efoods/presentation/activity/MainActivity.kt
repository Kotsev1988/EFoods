package com.example.efoods.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.efoods.App
import com.example.efoods.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}