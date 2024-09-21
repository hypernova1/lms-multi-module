package org.sam.lms.domain.auth.application.payload.out

class TokenDto(val accessToken: String = "", val refreshToken: String = "", val type: String = "Bearer")