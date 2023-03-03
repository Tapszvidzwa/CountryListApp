package com.example.countryapp.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.countryapp.data.repository.database.CountryInfo
import com.example.countryapp.databinding.ViewHolderCountryListItemBinding

class CountryListEntryViewHolder(private val binding: ViewHolderCountryListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: CountryInfo) {
        binding.countryInfo = item
    }
}