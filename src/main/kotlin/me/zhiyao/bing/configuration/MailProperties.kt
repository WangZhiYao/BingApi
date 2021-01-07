package me.zhiyao.bing.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 *
 * @author WangZhiYao
 * @date 2021/1/7
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.mail")
data class MailProperties(
    val host: String,
    val port: String,
    val username: String,
    val password: String,
    val from: String,
    val to: String,
    val properties: Map<String, String>
)