package cn.nqy.ms.data

/**
 *    author : Panfei.pf
 *    date   : 2021/3/13 13:58
 */
data class DataResult(
    val page: String,
    val page_count: String,
    val status: String,
    val total_counts: String,
    val data: List<PhotoItem>
)
