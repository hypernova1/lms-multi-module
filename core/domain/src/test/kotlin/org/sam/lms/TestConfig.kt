package org.sam.lms

import org.springframework.context.annotation.ComponentScan

@ComponentScan(
    basePackages = [
        "org.sam.lms.domain",
        "org.sam.lms.infrastructure",
        "org.sam.lms.persistence",
    ]
)
class TestConfig {
}