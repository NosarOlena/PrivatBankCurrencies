package com.example.privatbankcurrencies.state

import com.example.privatbankcurrencies.item.CurrencyItem

sealed class State {
    object Initial : State()

    data class Loading(val selectedDate: String) : State()

    data class Success(val selectedDate: String, val currencyData: CurrencyItem) : State()

    data class Error(val selectedDate: String, val message: String) : State()
}
