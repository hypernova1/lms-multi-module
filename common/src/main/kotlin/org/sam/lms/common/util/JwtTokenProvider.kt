package org.sam.lms.common.util

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${secret.jwt.security-key}") private val jwtSecret: String,
) {

//    private val log = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    fun generateAccessToken(userId: String): String {
        val tokenInvalidTime = 1000L * 60 * 60 * 24
        return this.createAccessToken(userId, tokenInvalidTime)
    }

    fun generateRefreshToken(userId: String): String {
        val refreshTokenInvalidTime = 1000L * 60 * 60 * 24 * 7
        return this.createRefreshToken(userId, refreshTokenInvalidTime)
    }

    private fun createAccessToken(userId: String, tokenInvalidTime: Long): String {
        return Jwts.builder()
            .subject(userId)
            .issuer("szs")
            .issuedAt(Date())
            .expiration(Date(Date().time + TimeUnit.HOURS.toMillis(tokenInvalidTime)))
            .claim("userId", userId)
            .claim("type", "access")
            .signWith(getSecretKey())
            .compact()
    }

    private fun createRefreshToken(userId: String, tokenInvalidTime: Long): String {
        return Jwts.builder()
            .subject(userId)
            .issuer("szs")
            .issuedAt(Date())
            .expiration(Date(Date().getTime() + TimeUnit.HOURS.toMillis(tokenInvalidTime)))
            .claim("userId", userId)
            .claim("type", "refresh")
            .signWith(getSecretKey())
            .compact()
    }

    private fun getSecretKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
        return Keys.hmacShaKeyFor(keyBytes)
    }


    fun getPayload(token: String?): String? {
        try {
            val claimsJws = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)


            if (claimsJws.payload!!["type"] != "access") {
                throw UnauthorizedException(ErrorCode.UNABLE_TO_REGISTER_USER)
            }
            val id = claimsJws.payload.get("userId", String::class.java)
            return id
        } catch (e: SecurityException) {
//            log.info("Invalid JWT Token", e)
        } catch (e: MalformedJwtException) {
//            log.info("Invalid JWT Token", e)
        } catch (e: ExpiredJwtException) {
//            log.info("Expired JWT Token", e)
        } catch (e: UnsupportedJwtException) {
//            log.info("Unsupported JWT Token", e)
        } catch (e: IllegalArgumentException) {
//            log.info("JWT claims string is empty.", e)
        }
        return null
    }

}