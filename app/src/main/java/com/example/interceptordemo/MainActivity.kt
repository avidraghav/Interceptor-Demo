package com.example.interceptordemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.interceptordemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder = OkHttpClient()
            .newBuilder()
            .addInterceptor(DemoInterceptor(applicationContext))
            .build()

        val experiment_api by lazy {
            Retrofit.Builder()
                .baseUrl("https://www.experimnetapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder)
                .build()
                .create(ExperimentApi::class.java)
        }

        binding.btRequest.setOnClickListener {

            lifecycleScope.launch {
                val response = experiment_api.getFeature()
                binding.tvActivity.text = response.body()?.title
            }
        }
    }
}