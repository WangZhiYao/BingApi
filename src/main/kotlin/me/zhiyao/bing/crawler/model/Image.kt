package me.zhiyao.bing.crawler.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @author WangZhiYao
 * @date 2020/12/23
 */
data class Image(
    @JsonProperty("enddate")
    val endDate: String?,
    val url: String?,
    val copyright: String?,
    val title: String?,
    val desc: String?
)