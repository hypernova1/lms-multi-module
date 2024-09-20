package org.sam.gateway.account

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("role")
class Role(
    @Id
    @Column("id")
    val id: Long,
    @Column("name")
    val name: String,
)