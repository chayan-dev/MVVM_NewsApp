package com.example.mvvm_newsapp.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(inputDate: String): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    return date?.let { outputFormat.format(it) }
}