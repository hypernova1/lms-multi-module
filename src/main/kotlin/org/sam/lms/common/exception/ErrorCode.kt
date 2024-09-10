package org.sam.lms.common.exception

enum class ErrorCode(val code: String, val message: String) {
    // 유저
    ALREADY_EXISTED_USER_ID("1000", "이미 사용되고 있는 아이디입니다."),
    UNABLE_TO_REGISTER_USER("1001", "가입할 수 없는 유저입니다."),
    ACCOUNT_NOT_FOUND("1002", "유저를 찾을 수 없습니다."),

    // 권한
    ACCOUNT_MISMATCH("2000", "계정 정보가 일치하지 않습니다."),
    UNAUTHORIZED_TOKEN("2001", "토큰 정보가 유효하지 않습니다."),

    // 요청
    MISMATCH_PARAMETER("3000", ""),
}