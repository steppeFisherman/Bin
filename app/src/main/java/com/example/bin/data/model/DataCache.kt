package com.example.bin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class DataCache(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val binNumber: String,
    val brand: String,
    val prepaid: Boolean,
    val scheme: String,
    val type: String,

    val city: String,
    val nameBank: String,
    val phone: String,
    val url: String,

    val alpha2: String,
    val currency: String,
    val emoji: String,
    val latitude: Int,
    val longitude: Int,
    val nameCountry: String,
    val numeric: String,

    val length: Int,
    val luhn: Boolean

)

