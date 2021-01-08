package me.zhiyao.bing.scheduler

import kotlinx.coroutines.runBlocking
import me.zhiyao.bing.crawler.BingCrawler
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 *
 * @author WangZhiYao
 * @date 2020/12/22
 */
@Component
class CrawlerScheduler(
    private val bingCrawler: BingCrawler,
    private val mailSender: JavaMailSender,
    private val message: SimpleMailMessage
) {

    @Scheduled(cron = "5 0 0 * * *")
    fun crawler() {
        runBlocking {
            if (!bingCrawler.getHPImageArchive()) {
                mailSender.send(message)
            }
        }
    }
}