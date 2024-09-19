package org.sam.lms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class LmsApplication

fun main(args: Array<String>) {
    runApplication<LmsApplication>(*args)
}
