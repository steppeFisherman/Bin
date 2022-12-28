package com.example.bin.utils

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.snackLong(@StringRes message: Int) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .show()
    this.textAlignment = View.TEXT_ALIGNMENT_CENTER
}

fun View.visible(show: Boolean) =
    if (show) this.visibility = View.VISIBLE else this.visibility = View.INVISIBLE