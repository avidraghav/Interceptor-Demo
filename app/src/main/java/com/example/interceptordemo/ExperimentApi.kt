package com.example.interceptordemo

import retrofit2.Response
import retrofit2.http.GET

interface ExperimentApi {

    // api we want to test
    @GET("feature")
    suspend fun getFeature(): Response<FeatureResponse>

}