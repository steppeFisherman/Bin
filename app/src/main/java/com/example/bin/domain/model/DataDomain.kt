package com.example.bin.domain.model

data class DataDomain(
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