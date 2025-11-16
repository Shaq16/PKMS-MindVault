package com.example.pkm_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CaptureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)

        val captureInput = findViewById<EditText>(R.id.inputThought)
        val saveBtn = findViewById<Button>(R.id.saveBtn)

        saveBtn.setOnClickListener {
            val text = captureInput.text.toString().trim()

            if (text.isEmpty()) {
                Toast.makeText(this, "Please enter a thought", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Thought saved!", Toast.LENGTH_SHORT).show()
            captureInput.text.clear()
        }
    }
}
