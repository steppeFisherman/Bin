package com.example.bin.utils

import android.text.Editable
import android.text.TextWatcher

typealias TextChangeListener = (textChanged: Editable?) -> Unit

class TextWatcher(private val listener: TextChangeListener) : TextWatcher {
    override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (charSequence.isNullOrBlank()) return
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(textChanged: Editable?) {
        if (!textChanged.isNullOrBlank()) listener(textChanged)
    }
}