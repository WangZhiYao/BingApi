package me.zhiyao.bing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BingApplication

fun main(args: Array<String>) {
    runApplication<BingApplication>(*args)
}
