package com.example.shibarichat

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shibarichat.Item

class ItemActivity : AppCompatActivity(){
    lateinit var data: ArrayList<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_activity)
        var index = 0
        val button = findViewById<Button>(R.id.button)
        val nameInput = findViewById<EditText>(R.id.name)
        val priceInput = findViewById<EditText>(R.id.price)
        val linkInput = findViewById<EditText>(R.id.link)
        val wishRv = findViewById<RecyclerView>(R.id.wishRv)
        data = ArrayList()
        var adapter = WishlistAdapter(data)
        wishRv.adapter = adapter
        wishRv.layoutManager = LinearLayoutManager(this)
        button.setOnClickListener {
            data.add(Item(nameInput.text.toString(), priceInput.text.toString(), linkInput.text.toString()))
            adapter.notifyItemInserted(index)

            index++

            uploadToFirebase()
        }
    }
}