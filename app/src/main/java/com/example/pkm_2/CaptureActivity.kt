package com.example.pkm_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pkm_2.viewmodel.MainViewModel
import com.example.pkm_2.viewmodel.UiState
import kotlinx.coroutines.launch

class CaptureActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)

        val input = findViewById<EditText>(R.id.inputThought)
        val saveBtn = findViewById<Button>(R.id.saveBtn)

        saveBtn.setOnClickListener {
            val text = input.text.toString().trim()
            if (text.isNotEmpty()) {
                saveBtn.isEnabled = false
                viewModel.ingestData(text)
            } else {
                Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            viewModel.ingestState.collect { state ->
                when (state) {
                    is UiState.Idle -> {
                        saveBtn.isEnabled = true
                    }
                    is UiState.Loading -> {
                        saveBtn.isEnabled = false
                        Toast.makeText(
                            this@CaptureActivity,
                            "Saving...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is UiState.Success -> {
                        saveBtn.isEnabled = true
                        Toast.makeText(
                            this@CaptureActivity,
                            state.data,
                            Toast.LENGTH_SHORT
                        ).show()
                        input.text.clear()
                    }
                    is UiState.Error -> {
                        saveBtn.isEnabled = true
                        Toast.makeText(
                            this@CaptureActivity,
                            "Error: ${state.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
<<<<<<< HEAD
}
// Added a comment to force re-analysis
=======
}
>>>>>>> d01ba51 (Initial commit: Android PKM app with Langflow integration)
