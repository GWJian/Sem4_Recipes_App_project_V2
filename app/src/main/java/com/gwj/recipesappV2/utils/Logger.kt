package com.gwj.recipesappV2.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class Logger:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val buffer = okio.Buffer()
        request.body?.writeTo(buffer)
        Log.d("loggingrequest", "request body: ${request.body?.toString()}")
        Log.d("loggingurl", "${request.method} request to : ${request.url}")
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(request)
        val duration = System.currentTimeMillis() - startTime
        val responseString = response.peekBody(Long.MAX_VALUE)
        Log.d("loggingresponse", responseString.string())
        Log.d("loggingtimetook", "It took $duration milliseconds")
        return response
    }
}