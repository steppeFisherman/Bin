package com.example.bin.utils

interface FormatCountryName {

    fun modify(alpha: String, countryName: String): String

    class Base : FormatCountryName {
        override fun modify(alpha: String, countryName: String): String {
            return if (alpha.length == 2) {
                val index2 = 2
                val index3 = 3

                val charList = alpha.toCharArray().toMutableList()
                charList.add(index2, ':')
                charList.add(index3, ' ')

                val charArray = charList.toCharArray()
                charArray.joinToString("")

                String(charArray) + countryName

            } else alpha + countryName
        }
    }
}