package org.sam.gateway.account

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
class Account(
    @Id
    @Column
    val id: Long = 0L,
    @Column
    val email: String = "",
    @Column
    val password: String = "",
    @Column("role_id")
    val roleId: Long,
    @Column("deleted_date")
    val deletedDate: LocalDateTime? = null

)