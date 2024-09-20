package org.sam.lms.api.config

/**
 * 핸들러에서 Provider 를 주입받을시 인증이 필요한 경우에 선언한다.
 * */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireAuth
