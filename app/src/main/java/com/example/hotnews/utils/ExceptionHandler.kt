package com.example.hotnews.utils

import java.net.UnknownHostException

object ExceptionHandler {

    fun handleException(throwable: Throwable) {
        if (throwable.message != null) {
            if (throwable is UnknownHostException) {
               
            }
        }
    }
}