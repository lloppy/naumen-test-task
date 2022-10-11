package com.example.shibarichat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shibarichat.Item
import com.example.shibarichat.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ItemActivity : AppCompatActivity(){
    lateinit var data: ArrayList<Item>
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var adapter: UserAdapter
    private val PERMISSION_REQUEST_CODE = 200
    lateinit var name: String

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_activity)

        auth = Firebase.auth
        name = auth.currentUser!!.displayName.toString()

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

            database = Firebase.database
            myRef = database.getReference("points")
            myRef.child(myRef.push().key ?: "blablabla")
                .setValue(Item(nameInput.text.toString(), priceInput.text.toString(), linkInput.text.toString()))


            button.isInvisible = true
            Toast.makeText(
                this,
                "Вишлист составлен",
                Toast.LENGTH_LONG
            ).show()


        }
    }
}