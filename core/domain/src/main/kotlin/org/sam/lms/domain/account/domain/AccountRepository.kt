package org.sam.lms.domain.account.domain

interface AccountRepository {
    fun findByEmail(email: String): Account?
    fun existsByEmail(email: String): Boolean
    fun save(accountEntity: Account): Account
    fun deleteById(id: Long)
}