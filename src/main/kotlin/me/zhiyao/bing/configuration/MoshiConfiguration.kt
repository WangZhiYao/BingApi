package me.zhiyao.bing.configuration

import com.squareup.moshi.Moshi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author WangZhiYao
 * @date 2020/12/24
 */
@Configuration
class MoshiConfiguration {

    @Bean
    fun moshi(): Moshi = Moshi.Builder()
        .build()
}