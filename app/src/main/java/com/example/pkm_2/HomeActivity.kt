<<<<<<< HEAD
=======
package com.example.pkm_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val captureBtn = findViewById<Button>(R.id.captureBtn)
        val queryBtn = findViewById<Button>(R.id.queryBtn)

        captureBtn.setOnClickListener {
            startActivity(Intent(this, CaptureActivity::class.java))
        }

        queryBtn.setOnClickListener {
            startActivity(Intent(this, QueryActivity::class.java))
        }
    }
}
>>>>>>> d01ba51 (Initial commit: Android PKM app with Langflow integration)
