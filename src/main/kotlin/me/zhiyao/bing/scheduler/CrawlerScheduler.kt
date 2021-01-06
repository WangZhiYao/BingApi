package me.zhiyao.bing.scheduler

import kotlinx.coroutines.runBlocking
import me.zhiyao.bing.crawler.BingCrawler
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 *
 * @author WangZhiYao
 * @date 2020/12/22
 */
@Component
class CrawlerScheduler(private val bingCrawler: BingCrawler) {

    @Scheduled(cron = "0 3 0 * * *")
    fun crawler() {
        runBlocking {
            bingCrawler.getHPImageArchive()
        }
    }
}