package org.sam.lms.account.ui

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.sam.lms.account.application.AccountService
import org.sam.lms.account.application.payload.`in`.AccountJoinRequest
import org.sam.lms.account.application.payload.out.AccountSummary
import org.sam.lms.account.domain.Account
import org.sam.lms.course.application.payload.out.CourseSummary
import org.sam.lms.infra.security.annotation.AuthUser
import org.sam.lms.infra.swagger.annotation.SwaggerCreatedResponse
import org.sam.lms.infra.swagger.annotation.SwaggerOkResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@Tag(name = "회원")
@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(private val accountService: AccountService) {

    @Operation(summary = "회원 가입")
    @SwaggerCreatedResponse(summary = "가입 성공", content = [Content(schema = Schema(implementation = AccountSummary::class))])
    @PostMapping
    fun create(@Valid @RequestBody accountJoinRequest: AccountJoinRequest): ResponseEntity<AccountSummary> {
        val accountSummary = this.accountService.create(accountJoinRequest)
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .replacePath("/api/v1/accounts/{id}")
            .buildAndExpand(accountSummary.id)
            .toUri()
        return ResponseEntity.created(location).body(accountSummary)
    }

    @Operation(summary = "회원 탈퇴")
    @SwaggerOkResponse(summary = "탈퇴 성공")
    @DeleteMapping("/me")
    fun delete(@AuthUser account: Account): ResponseEntity<Void> {
        accountService.delete(account)
        return ResponseEntity.ok().build()
    }

}