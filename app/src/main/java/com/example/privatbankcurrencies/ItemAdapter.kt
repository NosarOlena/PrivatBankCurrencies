package com.example.privatbankcurrencies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.privatbankcurrencies.databinding.ExchangeRateBinding
import com.example.privatbankcurrencies.item.ExchangeRate

class ItemAdapter(private val items: List<ExchangeRate>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ExchangeRateBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ExchangeRate) {
            binding.tvCurrency1.text = item.baseCurrency
            binding.tvCurrency2.text = item.currency

            binding.tvBuy.text = " Buy: ${item.purchaseRate}"
            binding.tvSale.text = "Sale: ${item.saleRate}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExchangeRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
