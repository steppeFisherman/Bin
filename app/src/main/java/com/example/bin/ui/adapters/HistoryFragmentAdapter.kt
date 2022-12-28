package com.example.bin.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bin.R
import com.example.bin.databinding.BinCardRvItemBinding
import com.example.bin.ui.model.DataUi

class HistoryFragmentAdapter(private val listener: Listener) :
    ListAdapter<DataUi, HistoryFragmentAdapter.MainHolder>(ItemCallback),
    View.OnClickListener {

    override fun onClick(v: View) {
        val bin = v.tag as DataUi
        when (v.id) {
            R.id.bank_url_history -> listener.toUrl(bin)
            R.id.bank_phone_history -> listener.dial(bin)
            R.id.btn_location_history -> listener.toLocation(bin)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = BinCardRvItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        view.bankUrlHistory.setOnClickListener(this)
        view.bankPhoneHistory.setOnClickListener(this)
        view.btnLocationHistory.setOnClickListener(this)

        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val bin = getItem(position)

        holder.binding.apply {
            root.tag = bin
            bankUrlHistory.tag = bin
            bankPhoneHistory.tag = bin
            btnLocationHistory.tag = bin

            binValue.text = bin.binNumber
            bankName.text = bin.nameBank
            bankUrlHistory.text = bin.url
            bankPhoneHistory.text = bin.phone
            schemeType.text = bin.scheme
            brandType.text = bin.brand
            lengthValue.text = bin.length.toString()
            if (bin.luhn) luhnYes.setTextColor(Color.BLACK) else luhnNo.setTextColor(Color.BLACK)
            if (bin.type == "debit") typeDebit.setTextColor(Color.BLACK)
            else typeCredit.setTextColor(Color.BLACK)
            if (bin.prepaid) prepaidYes.setTextColor(Color.BLACK)
            else prepaidNo.setTextColor(Color.BLACK)
            countryName.text = bin.nameCountry
            lat.text = holder.itemView.context
                .getString(R.string.lat_placeHolder, bin.latitude.toString())
            lng.text = holder.itemView.context
                .getString(R.string.lng_placeHolder, bin.longitude.toString())
        }
    }

    class MainHolder(val binding: BinCardRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun toUrl(bin: DataUi)
        fun dial(bin: DataUi)
        fun toLocation(bin: DataUi)
    }

    object ItemCallback : DiffUtil.ItemCallback<DataUi>() {
        override fun areItemsTheSame(oldItem: DataUi, newItem: DataUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataUi, newItem: DataUi): Boolean {
            return oldItem == newItem
        }
    }
}