package com.example.hotnews.utils

import java.lang.Exception

sealed class Result<out T> {
    data class Success<out D>(val data: D) : Result<D>()
    data class Error(val exception: Exception) : Result<Nothing>()
}