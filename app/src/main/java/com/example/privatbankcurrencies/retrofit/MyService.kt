package com.example.privatbankcurrencies.retrofit

import com.example.privatbankcurrencies.item.CurrencyItem
import retrofit2.http.GET
import retrofit2.http.Query

interface MyService {
    // https://api.privatbank.ua/p24api/exchange_rates?date=01.12.2014

    @GET("p24api/exchange_rates")
    suspend fun getCurrencyExchange(
        @Query("date") date : String
    ) : CurrencyItem
}
