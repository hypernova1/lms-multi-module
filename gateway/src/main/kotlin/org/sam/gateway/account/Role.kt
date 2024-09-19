package org.sam.gateway.account

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("role")
class Role(
    @Id
    val id: Long,
    val name: String,
)