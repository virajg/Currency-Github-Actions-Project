package org.example.cmp.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.cmp.BuildKonfig
import org.example.cmp.data.model.CurrencyResponseDTO
import org.example.cmp.data.remote.utils.safeApiCall
import org.example.cmp.domain.utils.ApiResult

object CurrencyApi {

  private  val buildHttpClient =  HttpClient{
        install(ContentNegotiation){
            json(
                json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging){
            level = LogLevel.ALL
        }

        defaultRequest {
            header("apikey", BuildKonfig.API_KEY)
            url(BuildKonfig.CURRENCY_BASE_URL)
        }
    }

    suspend fun getLastedRates(): ApiResult<CurrencyResponseDTO> = safeApiCall {
        buildHttpClient.get(ApiRoutes.LASTED_EXCHANGE_RATES)
    }

}