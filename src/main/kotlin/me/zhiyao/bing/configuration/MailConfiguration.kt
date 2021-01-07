package me.zhiyao.bing.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*


/**
 *
 * @author WangZhiYao
 * @date 2020/12/24
 */
@Configuration
@EnableConfigurationProperties(MailProperties::class)
class MailConfiguration(
    private val mailProperties: MailProperties
) {

    @Bean
    fun mailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = mailProperties.host
        mailSender.port = mailProperties.port.toInt()
        mailSender.username = mailProperties.username
        mailSender.password = mailProperties.password
        val properties = Properties()
        properties.putAll(mailProperties.properties)
        mailSender.javaMailProperties = properties
        return mailSender
    }

    @Bean
    fun message(): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.setFrom(mailProperties.from)
        message.setTo(mailProperties.to)
        message.setSubject("无法获取Bing每日图片")
        message.setText("具体信息请查看日志")
        return message
    }
}