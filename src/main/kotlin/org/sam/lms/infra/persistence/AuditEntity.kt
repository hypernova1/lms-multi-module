package org.sam.lms.infra.persistence

import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class AuditEntity {

    @CreatedDate
    val createdDate: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    val updatedDate: LocalDateTime = LocalDateTime.now()

}