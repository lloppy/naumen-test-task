package com.example.shibarichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        supportActionBar?.hide()

        var button = findViewById(R.id.button) as Button
        button.setOnClickListener {
            val intent = Intent(this, StartSecondActivity::class.java)
            startActivity(intent)
        }
    }
}