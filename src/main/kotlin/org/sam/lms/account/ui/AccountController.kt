package org.sam.lms.account.ui

import org.sam.lms.account.application.AccountService
import org.sam.lms.account.application.payload.`in`.AccountJoinRequest
import org.sam.lms.account.application.payload.out.AccountSummary
import org.sam.lms.account.domain.Account
import org.sam.lms.infra.security.annotation.AuthUser
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    fun create(@RequestBody accountJoinRequest: AccountJoinRequest): ResponseEntity<AccountSummary> {
        val accountSummary = this.accountService.create(accountJoinRequest)
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .replacePath("/api/v1/accounts/{id}")
            .buildAndExpand(accountSummary.id)
            .toUri()
        return ResponseEntity.created(location).body(accountSummary)
    }

    @DeleteMapping("/me")
    fun delete(@AuthUser account: Account): ResponseEntity<Void> {
        accountService.delete(account)
        return ResponseEntity.ok().build()
    }

}