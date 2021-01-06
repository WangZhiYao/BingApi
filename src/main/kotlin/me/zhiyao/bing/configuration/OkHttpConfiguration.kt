package me.zhiyao.bing.configuration

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 *
 * @author WangZhiYao
 * @date 2020/12/23
 */
@Configuration
class OkHttpConfiguration {

    companion object {
        private const val DEFAULT_TIME_OUT = 60L
    }

    @Bean
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        .build()
}