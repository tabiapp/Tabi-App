package com.example.tabi.utils
import android.content.Context
import android.view.View
import android.widget.Toast

// Ekstensi untuk menampilkan Toast
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// Ekstensi untuk membuat View menjadi terlihat
fun View.show() {
    this.visibility = View.VISIBLE
}

// Ekstensi untuk menyembunyikan View
fun View.hide() {
    this.visibility = View.GONE
}

// Ekstensi untuk mengganti View menjadi invisible
fun View.invisible() {
    this.visibility = View.INVISIBLE
}