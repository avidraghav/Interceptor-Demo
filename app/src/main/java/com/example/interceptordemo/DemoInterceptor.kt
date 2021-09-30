package com.example.interceptordemo

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.BufferedReader

class DemoInterceptor(private val context: Context) : Interceptor {

    private var contentType = "application/json"

    override fun intercept(chain: Interceptor.Chain): Response {

        val uri = chain.request().url.toUrl()

        when {
            uri.toString()
                .contains("https://www.experimnetapi.com/feature") -> {
                return getResponse(chain, R.raw.testresponse)
            }
        }
        return chain.proceed(chain.request())
    }

    fun getResponse(chain: Interceptor.Chain, resId: Int): Response {
        val jsonString = this.context.resources
            .openRawResource(resId)
            .bufferedReader()
            .use(BufferedReader::readText)

        val builder = Response.Builder()
        builder.request(chain.request())
        builder.protocol(Protocol.HTTP_2)
        builder.addHeader("content-type", contentType)
        builder.body(
            jsonString.
            toByteArray().
            toResponseBody(contentType.toMediaTypeOrNull())
        )
        builder.code(200)
        builder.message(jsonString)
        return builder.build()
    }
}