package cn.nqy.ms.data

/**
 *    author : Panfei.pf
 *    date   : 2021/3/13 13:47
 */
data class PhotoItem(
    val _id: String,
    val author: String,
    val category: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val likeCounts: String,
    val publishedAt: String,
    val stars: String,
    val title: String,
    val type: String,
    val url: String,
    val views: String
)
