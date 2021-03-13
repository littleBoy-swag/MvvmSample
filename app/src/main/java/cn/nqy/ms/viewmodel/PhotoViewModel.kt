package cn.nqy.ms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.nqy.ms.data.DataResult
import cn.nqy.ms.data.PhotoItem
import cn.nqy.ms.repo.PhotoRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *    author : Panfei.pf
 *    date   : 2021/3/13 13:46
 */
class PhotoViewModel : ViewModel() {

    private var photoList: MutableLiveData<List<PhotoItem>> = MutableLiveData()

    fun getPhotoList(): LiveData<List<PhotoItem>> = photoList

    fun requestPhotoList(page: Int, count: Int = 10) {
        PhotoRepo.getPhotos(page, count).enqueue(object : Callback<DataResult> {
            override fun onResponse(call: Call<DataResult>, response: Response<DataResult>) {
                response.body()?.apply {
                    photoList.value = data
                }
            }

            override fun onFailure(call: Call<DataResult>, t: Throwable) {
                photoList.value = null
                t.printStackTrace()
            }
        })
    }


}