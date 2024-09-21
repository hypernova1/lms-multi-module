package org.sam.lms.api.account

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.sam.lms.api.account.request.CreateJoinRequest
import org.sam.lms.api.swagger.annotation.SwaggerCreatedResponse
import org.sam.lms.api.swagger.annotation.SwaggerOkResponse
import org.sam.lms.domain.account.application.AccountService
import org.sam.lms.domain.account.application.payload.`in`.CreateAccountDto
import org.sam.lms.domain.account.application.payload.out.AccountSummary
import org.sam.lms.domain.account.domain.Provider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@Tag(name = "회원")
@RestController
@RequestMapping("/lms/api/v1/accounts")
class AccountController(private val accountService: AccountService) {

    @Operation(summary = "회원 가입")
    @SwaggerCreatedResponse(
        summary = "가입 성공",
        content = [Content(schema = Schema(implementation = AccountSummary::class))]
    )
    @PostMapping
    fun create(@Valid @RequestBody createJoinRequest: CreateJoinRequest): ResponseEntity<AccountSummary> {
        val accountSummary = this.accountService.create(
            CreateAccountDto(
                email = createJoinRequest.email,
                password = createJoinRequest.password,
                name = createJoinRequest.name,
                role = createJoinRequest.role,
            )
        )
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
    fun delete(
        provider: Provider
    ): ResponseEntity<Void> {
        accountService.delete(provider)
        return ResponseEntity.ok().build()
    }

}