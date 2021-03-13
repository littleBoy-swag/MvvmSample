package cn.nqy.ms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.nqy.annotation.NqyRouter
import cn.nqy.ms.data.PhotoItem
import cn.nqy.ms.utils.RecyclerViewScrollListener
import cn.nqy.ms.viewmodel.PhotoViewModel
import com.bumptech.glide.Glide

@NqyRouter(path = "app/MainActivity")
class MainActivity : AppCompatActivity() {

    private lateinit var rvPhotos: RecyclerView
    private var page = 1
    private var photoList = ArrayList<PhotoItem>()
    private var loadMore = true // 是否加载下一页

    private lateinit var photoViewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRvPhotos()
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        photoViewModel.getPhotoList().observe(this) { list ->
            if (list.isNullOrEmpty()) {
                loadMore = false
                Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show()
                return@observe
            }
            page++
            val prePosition = photoList.size
            photoList.addAll(list)
            rvPhotos.adapter?.notifyItemInserted(prePosition)
        }
        photoViewModel.requestPhotoList(page)
    }

    private fun initRvPhotos() {
        rvPhotos = findViewById(R.id.rvPhotos)
        rvPhotos.adapter = PhotoAdapter()
        rvPhotos.layoutManager = LinearLayoutManager(this)
        // RecyclerView滑动到底部监听，方法随手在网上找的
        rvPhotos.addOnScrollListener(object : RecyclerViewScrollListener() {
            override fun onScrollToBottom() {
                super.onScrollToBottom()
                if (loadMore) {
                    photoViewModel.requestPhotoList(page)
                } else {
                    Toast.makeText(this@MainActivity, "没有更多数据了", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    inner class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
            return PhotoViewHolder(
                LayoutInflater.from(this@MainActivity).inflate(
                    R.layout.layout_item_photo, parent, false
                )
            )
        }

        override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
            Glide.with(this@MainActivity)
                .load(photoList[position].url)
                .into(holder.ivPhoto)
        }

        override fun getItemCount(): Int = photoList.size

        inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val ivPhoto: ImageView = itemView.findViewById(R.id.ivPhoto)
        }

    }

}