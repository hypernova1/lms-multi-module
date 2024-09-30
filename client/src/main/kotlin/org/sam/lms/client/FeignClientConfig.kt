package org.sam.lms.client

import feign.Client
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableFeignClients
@Configuration
class FeignClientConfig {

    @Bean
    fun feignClient(): Client {
        return Client.Default(null, null)
    }

}