package cn.nqy.ms.net

import cn.nqy.ms.data.DataResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *    author : Panfei.pf
 *    date   : 2021/3/13 13:57
 */
interface ApiService {

    /**
     * 获取图片
     */
    @GET("page/{page}/count/{count}")
    fun getPhotos(@Path("page") page: Int, @Path("count") count: Int): Call<DataResult>

}