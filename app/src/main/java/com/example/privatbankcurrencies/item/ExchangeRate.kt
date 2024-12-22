package com.example.privatbankcurrencies.item


import com.google.gson.annotations.SerializedName

data class ExchangeRate(
    @SerializedName("baseCurrency")
    val baseCurrency: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("purchaseRateNB")
    val purchaseRate: Double?,
    @SerializedName("saleRateNB")
    val saleRate: Double?
)
