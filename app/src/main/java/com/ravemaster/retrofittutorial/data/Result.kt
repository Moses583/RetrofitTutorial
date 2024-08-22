package com.ravemaster.retrofittutorial.data
//this is similar to the listener interface in the java implementation
sealed class Result<T> (
    val response: T? = null,
    val message: String? = null
){
    class Success<T> (response: T?): Result<T>(response)
    class Failure<T> (response: T? = null, message: String?):Result<T>(response, message)
}