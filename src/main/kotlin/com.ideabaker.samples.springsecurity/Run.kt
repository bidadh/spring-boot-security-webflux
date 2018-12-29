package com.ideabaker.samples.springsecurity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-28 16:16
 */
@SpringBootApplication
@EnableReactiveMongoRepositories
class Run

fun main(args: Array<String>) {
    runApplication<Run>(*args)
}
