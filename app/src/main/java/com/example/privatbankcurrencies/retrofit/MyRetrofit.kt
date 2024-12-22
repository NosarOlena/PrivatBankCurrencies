package com.example.privatbankcurrencies.retrofit

import com.example.privatbankcurrencies.item.CurrencyItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit {
    private val baseUrl = "https://api.privatbank.ua/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service : MyService = retrofit.create(MyService::class.java)

    suspend fun getCurrencyExchange(date: String): CurrencyItem {
        return service.getCurrencyExchange(date)
    }

}