package com.example.shibarichat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shibarichat.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


class ItemActivity : AppCompatActivity(){
    lateinit var data: ArrayList<Item>
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var name: String

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var allDataValues: ArrayList<String>

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
        loadDataFromFirebase()
        allDataValues = ArrayList()

        var adapter = WishlistAdapter(data)
        wishRv.adapter = adapter
        wishRv.layoutManager = LinearLayoutManager(this)

        button.setOnClickListener {

            data.add(Item(nameInput.text.toString(), priceInput.text.toString(), linkInput.text.toString()))

            index++
            Toast.makeText(this, "Добавлено\nОбязательно сохраните свой список!", Toast.LENGTH_LONG).show()

            clearEditText(nameInput)
            clearEditText(priceInput)
            clearEditText(linkInput)
        }
    }

    private fun loadDataFromFirebase() {
        val reference = FirebaseDatabase.getInstance().reference.child(name)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val nameFb = dataSnapshot.child("name").value.toString()
                    val linkFb = dataSnapshot.child("link").value.toString()
                    val priceFb = dataSnapshot.child("price").value.toString()

                    data.add(Item(nameFb, priceFb, linkFb))
                    allDataValues.add("$nameFb за $priceFb. Ссылка: $linkFb")
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            writeDataToFirebase()
        }
        if (item.itemId == R.id.share){

            val value = data.toString()
            val name = name.toString()
            val array = allDataValues

            var link = ""

            for (arr in array){
                link += arr + "\n"
            }

            val i = Intent(this@ItemActivity, QRActivity::class.java)
            i.putExtra("key", value)
            i.putExtra("name", name)
            i.putExtra("array", link)

            Log.e("data", data.toString())
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun writeDataToFirebase() {
        database = Firebase.database
        myRef = database.getReference(name)
        myRef.setValue(data)

        Toast.makeText(this, "Сохранено", Toast.LENGTH_LONG).show()
    }

    private fun clearEditText(edMessage: EditText) {
        edMessage.setText("")
    }
}