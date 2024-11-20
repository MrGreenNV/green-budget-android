package ru.averkiev.budget.repositories

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import ru.averkiev.budget.models.CharacterResponse
import kotlin.time.Duration.Companion.seconds

interface KtorApi {
    suspend fun getCharacters(pageSize: Int, page: Int): List<CharacterResponse>
}

class KtorNetwork:  KtorApi {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(json)
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 20.seconds.inWholeMilliseconds
                requestTimeoutMillis = 60.seconds.inWholeMilliseconds
                socketTimeoutMillis = 20.seconds.inWholeMilliseconds
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    override suspend fun getCharacters(pageSize: Int, page: Int): List<CharacterResponse> {
        val response = try {
            client.get {
                url {
                    host = "www.anapioficeandfire.com"
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                    path("api", "characters")
                    parameters.append("page", "$page")
                    parameters.append("pageSize", "$pageSize")
                }
            }.body<List<CharacterResponse>>()
        } catch (ex: Exception) {
            Log.e("KtorNetwork", "Error fetching characters: ${ex.message}")
            emptyList()
        }

        Log.d("KtorNetwork", "raw = $response")
        return response
    }
}