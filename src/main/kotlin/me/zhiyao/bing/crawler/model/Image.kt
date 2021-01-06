package me.zhiyao.bing.crawler.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *
 * @author WangZhiYao
 * @date 2020/12/23
 */
@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "enddate")
    val endDate: String?,
    val url: String?,
    val copyright: String?,
    val title: String?,
    val desc: String?
)