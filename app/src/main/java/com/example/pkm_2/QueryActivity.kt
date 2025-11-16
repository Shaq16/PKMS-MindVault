package com.example.pkm_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QueryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)

        val queryInput = findViewById<EditText>(R.id.queryInput)
        val searchBtn = findViewById<Button>(R.id.searchBtn)
        val resultText = findViewById<TextView>(R.id.resultText)

        searchBtn.setOnClickListener {
            val query = queryInput.text.toString().trim()

            if (query.isEmpty()) {
                Toast.makeText(this, "Enter something to search", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Temporary result text
            resultText.text = "Result for: \"$query\" (placeholder)"
        }
    }
}
