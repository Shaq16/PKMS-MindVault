package com.example.pkm_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pkm_2.viewmodel.MainViewModel
import com.example.pkm_2.viewmodel.UiState
import kotlinx.coroutines.launch

class QueryActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)

        val input = findViewById<EditText>(R.id.queryInput)
        val searchBtn = findViewById<Button>(R.id.searchBtn)
        val resultText = findViewById<TextView>(R.id.resultText)

        searchBtn.setOnClickListener {
            val q = input.text.toString().trim()
            if (q.isNotEmpty()) {
                searchBtn.isEnabled = false
                viewModel.queryData(q)
            } else {
                resultText.text = "Please enter a question"
            }
        }

        lifecycleScope.launch {
            viewModel.queryState.collect { state ->
                when (state) {
                    is UiState.Idle -> {
                        searchBtn.isEnabled = true
                        resultText.text = ""
                    }
                    is UiState.Loading -> {
                        searchBtn.isEnabled = false
                        resultText.text = "Searching..."
                    }
                    is UiState.Success -> {
                        searchBtn.isEnabled = true
                        resultText.text = state.data
                    }
                    is UiState.Error -> {
                        searchBtn.isEnabled = true
                        resultText.text = "Error: ${state.message}"
                    }
                }
            }
        }
    }
}