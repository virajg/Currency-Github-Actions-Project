package org.example.cmp.presentation.components

expect object AppLogger{
    fun e(tag:String, message: String, throwable: Throwable? = null)
    fun d(tag:String, message: String)
    fun i(tag:String, message: String)
}