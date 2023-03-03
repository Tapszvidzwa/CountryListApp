package com.example.countryapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.countryapp.data.repository.database.CountryInfo
import com.example.countryapp.databinding.ViewHolderCountryListItemBinding

class CountryListAdapter :
    ListAdapter<CountryInfo, CountryListEntryViewHolder?>(CountryInfoDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryListEntryViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderCountryListItemBinding.inflate(layoutInflater, parent, false)

        return CountryListEntryViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: CountryListEntryViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

class CountryInfoDiffCallback : DiffUtil.ItemCallback<CountryInfo>() {
    override fun areItemsTheSame(oldItem: CountryInfo, newItem: CountryInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CountryInfo, newItem: CountryInfo): Boolean {
        return oldItem == newItem
    }
}

