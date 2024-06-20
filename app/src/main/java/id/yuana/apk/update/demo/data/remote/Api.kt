package id.yuana.apk.update.demo.data.remote

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface Api {

    @GET("v1/app/latest")
    suspend fun getAppLatest(): JsonObject

    @Streaming
    @GET
    suspend fun apkDownload(@Url apkUrl: String): Response<ResponseBody>
}