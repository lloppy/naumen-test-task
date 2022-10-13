package com.example.shibarichat

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.WriterException

var im: ImageView? = null

class QRActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qractivity)
        im = findViewById(R.id.imageView)

        val extras = intent.extras
        if (extras != null) {
            val data = extras.getString("key")
            val array = extras.getString("array")
            val name = extras.getString("name")

            var text = name + " хочет получить:\n\n" + array.toString()
            Log.e("data",text)

            generateQrCode(text)

        }
    }

    private fun generateQrCode(text: String) {
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 1000)
        try {
            val bMap = qrGenerator.bitmap
            im?.setImageBitmap(bMap)
        } catch (e: WriterException) {

        }
    }

}