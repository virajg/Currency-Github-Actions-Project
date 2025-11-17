package org.example.cmp.presentation.components

import android.util.Log

actual object AppLogger{

     actual fun e(tag:String,message:String,throwable: Throwable?){
         throwable?.let {
             Log.e(tag, message, throwable)
         }?:kotlin.run {
             Log.e(tag, message)
         }
     }

    actual fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    actual fun i(tag: String, message: String) {
        Log.i(tag, message)
    }
}