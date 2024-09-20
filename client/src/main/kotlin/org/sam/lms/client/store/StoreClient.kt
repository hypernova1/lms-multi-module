package org.sam.lms.client.store

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "localhost:8082")
interface StoreClient {
}