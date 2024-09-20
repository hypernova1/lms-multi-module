package org.sam.gateway.account

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.bind
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class AccountRepository(private val databaseClient: DatabaseClient) {

    fun findAccountWithRoleByEmail(email: String): Mono<AccountWithRole> {
        return databaseClient.sql("""
            SELECT a.id AS account_id, a.email, a.password, r.id AS role_id, r.name AS role_name
            FROM account a
            JOIN role r ON a.role_id = r.id
            WHERE a.email = :email
            AND a.deleted_date is null
        """).bind<String>("email", email)
            .map { row ->
                val accountId: Long = row.get("account_id") as Long
                val accountEmail = row.get("email", String::class.java)
                val accountPassword = row.get("password", String::class.java)
                val roleId = row.get("role_id") as Long
                val roleName = row.get("role_name", String::class.java)

                AccountWithRole(
                    Account(id = accountId, email = accountEmail!!, password = accountPassword!!, roleId = roleId),
                    Role(id = roleId, name = roleName!!)
                )
            }
            .one()
    }

}

data class AccountWithRole(val account: Account, val role: Role)