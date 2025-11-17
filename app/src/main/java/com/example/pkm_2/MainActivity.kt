package com.example.pkm_2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pkm_2.databinding.ActivityMainBinding
import com.example.pkm_2.repository.LangflowRepository
import com.example.pkm_2.viewmodel.MainUiState
import com.example.pkm_2.viewmodel.MainViewModel
import com.example.pkm_2.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val repository by lazy { LangflowRepository() }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.btnAddData.setOnClickListener {
            val data = binding.etDataInput.text.toString().trim()
            if (data.isNotBlank()) {
                viewModel.ingestData(data)
            } else {
                Toast.makeText(this, "Please enter data to ingest", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnQuery.setOnClickListener {
            val question = binding.etQueryInput.text.toString().trim()
            if (question.isNotBlank()) {
                viewModel.queryData(question)
            } else {
                Toast.makeText(this, "Please enter a question", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is MainUiState.Idle -> {
                        setLoading(false)
                    }
                    is MainUiState.Loading -> {
                        setLoading(true)
                    }
                    is MainUiState.Success -> {
                        setLoading(false)
                        binding.tvAnswer.text = state.message
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        binding.etDataInput.text.clear()
                        binding.etQueryInput.text.clear()
                    }
                    is MainUiState.Error -> {
                        setLoading(false)
                        binding.tvAnswer.text = "Error: ${state.message}"
                        Toast.makeText(this@MainActivity, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnAddData.isEnabled = !isLoading
        binding.btnQuery.isEnabled = !isLoading
        if (isLoading) {
            binding.tvAnswer.text = "Thinking..."
        }
    }
}
