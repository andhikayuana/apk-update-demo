package id.yuana.apk.update.demo.di

import id.yuana.apk.update.demo.data.remote.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    private val apiClient = Retrofit.Builder()
        .baseUrl("https://bb0f-104-28-251-244.ngrok-free.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun provideApiClient(): Api {
        return apiClient
    }
}