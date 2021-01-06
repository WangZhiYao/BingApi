package me.zhiyao.bing.crawler.model

import com.squareup.moshi.JsonClass

/**
 *
 * @author WangZhiYao
 * @date 2020/12/23
 */
@JsonClass(generateAdapter = true)
data class HPImageArchive(
    val images: List<Image>?
)
