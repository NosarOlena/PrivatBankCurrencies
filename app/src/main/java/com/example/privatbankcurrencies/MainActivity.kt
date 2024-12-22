package com.example.privatbankcurrencies

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.privatbankcurrencies.databinding.ActivityMainBinding
import com.example.privatbankcurrencies.state.State
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()

        viewModel.state.observe(this) { state ->
            when (state) {
                is State.Initial -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.etDate.hint = "Select a date"
                }
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.etDate.hint = state.selectedDate
                }
                is State.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.etDate.hint = state.selectedDate
                    val adapter = ItemAdapter(state.currencyData.exchangeRate)
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.recyclerView.adapter = adapter
                }
                is State.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    Log.e(TAG, "Error: ${state.message}")
                    binding.etDate.hint = state.selectedDate
                }
            }
        }

        binding.etDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                viewModel.onDateSelected(selectedYear, selectedMonth, selectedDay)
            }, year, month, day).show()
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
