// File: app/src/main/java/com/yourapp/MainActivity.kt
package com.example.pkm_2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pkm_2.databinding.ActivityMainBinding
import com.example.pkm_2.viewmodel.MainViewModel
import com.example.pkm_2.viewmodel.UiState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeStates()
    }

    private fun setupListeners() {
        // Add Data button
        binding.btnAddData.setOnClickListener {
            val data = binding.etDataInput.text.toString()
            if (data.isNotBlank()) {
                viewModel.ingestData(data)
                binding.etDataInput.text?.clear()
            } else {
                Toast.makeText(this, "Please enter data", Toast.LENGTH_SHORT).show()
            }
        }

        // Query button
        binding.btnQuery.setOnClickListener {
            val question = binding.etQueryInput.text.toString()
            if (question.isNotBlank()) {
                viewModel.queryData(question)
            } else {
                Toast.makeText(this, "Please enter a question", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeStates() {
        // Observe ingest state
        lifecycleScope.launch {
            viewModel.ingestState.collect { state ->
                when (state) {
                    is UiState.Idle -> {
                        binding.btnAddData.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                    }
                    is UiState.Loading -> {
                        binding.btnAddData.isEnabled = false
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.btnAddData.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, state.data, Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Error -> {
                        binding.btnAddData.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@MainActivity,
                            "Error: ${state.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        // Observe query state
        lifecycleScope.launch {
            viewModel.queryState.collect { state ->
                when (state) {
                    is UiState.Idle -> {
                        binding.btnQuery.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                    }
                    is UiState.Loading -> {
                        binding.btnQuery.isEnabled = false
                        binding.progressBar.visibility = View.VISIBLE
                        binding.tvAnswer.text = "Thinking..."
                    }
                    is UiState.Success -> {
                        binding.btnQuery.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                        binding.tvAnswer.text = state.data
                    }
                    is UiState.Error -> {
                        binding.btnQuery.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                        binding.tvAnswer.text = "Error: ${state.message}"
                    }
                }
            }
        }
    }
}
