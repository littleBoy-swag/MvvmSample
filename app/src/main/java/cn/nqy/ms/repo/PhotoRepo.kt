package cn.nqy.ms.repo

import android.util.Log
import cn.nqy.ms.data.DataResult
import cn.nqy.ms.net.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

/**
 *    author : Panfei.pf
 *    date   : 2021/3/13 13:49
 */
object PhotoRepo {

    private var retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://gank.io/api/v2/data/category/Girl/type/Girl/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient
                .Builder()
                .addInterceptor(HttpLoggingInterceptor { message ->
                    try {
                        val text = URLDecoder.decode(message, "utf-8");
                        Log.e("OKHttp-----", text)
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                        Log.e("OKHttp-----", message)
                    }
                }.also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()

    fun getPhotos(page: Int, count: Int): Call<DataResult> {
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.getPhotos(page, count)
    }


}