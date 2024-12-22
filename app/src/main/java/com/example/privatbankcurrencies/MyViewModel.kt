package com.example.privatbankcurrencies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.privatbankcurrencies.retrofit.MyRetrofit
import com.example.privatbankcurrencies.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MyViewModel : ViewModel() {

    private val retrofit = MyRetrofit()

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private var currentJob: Job? = null

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    init {
        _state.value = State.Initial
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, day)

        val formattedDate = dateFormat.format(selectedDate.time)

        currentJob?.cancel()

        _state.value = State.Loading(selectedDate = formattedDate)
        fetchCurrencyData(formattedDate)
    }

    private fun fetchCurrencyData(date: String) {
        _state.value = State.Loading(selectedDate = date)

        currentJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "Fetching data at: ${System.currentTimeMillis()}")
                val currencyItem = retrofit.getCurrencyExchange(date)
                Log.d(TAG, "Data fetched at: ${System.currentTimeMillis()}")

                _state.postValue(State.Success(selectedDate = date, currencyData = currencyItem))
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching currency data", e)
                _state.postValue(State.Error(selectedDate = date, message = e.message ?: "Unknown error"))
            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}
