package com.example.companytime.data

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenStorage: TokenStorage
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val path = chain.request().url.encodedPath

        val publish = path.contains("/api/person/register") || path.contains("/api/department")

        val token = tokenStorage.getToken()

        val request = chain.request()
            .newBuilder()
            .apply {
                if (!token.isNullOrBlank() && !publish) { header("Authorization", "Basic $token")}
            }
            .build()

        return chain.proceed(request)
    }
}