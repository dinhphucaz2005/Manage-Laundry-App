package com.example.manage.laundry.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val BASE_URL = ""

val httpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}


suspend inline fun <reified T> getApi(endpoint: String): T {
    return httpClient.get("$BASE_URL$endpoint").body()
}

suspend inline fun <reified T, reified R> postApi(
    endpoint: String,
    bodyData: R
): T {
    return httpClient.post("$BASE_URL$endpoint") {
        contentType(ContentType.Application.Json)
        setBody(bodyData)
    }.body()
}
