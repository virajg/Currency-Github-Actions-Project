package org.example.cmp.data.remote.utils

import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException
import org.example.cmp.domain.utils.ApiResult

// Define ranges using Kotlin's standard integer syntax, since allBelow is unavailable
val successRange = 200..299
val clientErrorRange = 400..499

suspend inline fun <reified R> safeApiCall(
    crossinline apiCall: suspend () -> HttpResponse
) : ApiResult<R> {
    return try {
        val response = apiCall()

        when(response.status.value){
            in successRange -> { // Check if status code is between 200 and 299
                val data = response.body<R>()
                ApiResult.Success(data)
            }
            in clientErrorRange -> { // Check if status code is between 400 and 499
                val errorDetails = try {
                    response.body<String>() // Attempt to read the error body
                } catch (e: Exception) {
                    "Could not parse error details."
                }
                ApiResult.Error(
                    errorMessage = "Client Error (${response.status.value}): $errorDetails",
                    errorCode = response.status.value,
                    throwable = null // No primary throwable for HTTP errors
                )
            }
            else -> { // Handles 1xx, 3xx, 5xx, and other codes
                ApiResult.Error(
                    errorMessage = "Server Error (${response.status.value}). Please try again later.",
                    errorCode = response.status.value,
                    throwable = null
                )
            }
        }
    } catch (e: ConnectTimeoutException) {
        // Handle internet connection loss or timeout
        ApiResult.Error(
            errorMessage = "Network timeout. Please check your internet connection.",
            throwable = e
        )
    } catch (e: HttpRequestTimeoutException) {
        ApiResult.Error(
            errorMessage = "Request timed out.",
            throwable = e
        )
    } catch (e: ClientRequestException) {
        // Ktor client exception for unhandled 4xx responses
        ApiResult.Error(
            errorMessage = "Client error occurred.",
            errorCode = e.response.status.value,
            throwable = e
        )
    } catch (e: ServerResponseException) {
        // Ktor server exception for unhandled 5xx responses
        ApiResult.Error(
            errorMessage = "Server error occurred.",
            errorCode = e.response.status.value,
            throwable = e
        )
    } catch (e: SerializationException) {
        // Handle JSON parsing errors
        ApiResult.Error(
            errorMessage = "Data parsing error. Response structure mismatch.",
            throwable = e
        )
    } catch (e: Exception) {
        // Catch any other unexpected exceptions
        ApiResult.Error(
            errorMessage = "An unexpected error occurred: ${e.message}",
            throwable = e
        )
    }
}