package com.example.bin.utils

interface FormatPhoneNumber {

    fun modify(initial: String): String

    class Base : FormatPhoneNumber {
        override fun modify(initial: String): String {
            return if (initial.isNotBlank()) {
                val index0 = 0

                val charList = initial.toCharArray().toMutableList()
                charList.add(index0, '+')

                val charArray = charList.toCharArray()
                charArray.joinToString("")

                String(charArray)

            } else initial
        }
    }
}