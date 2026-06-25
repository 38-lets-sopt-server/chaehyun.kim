package org.sopt.common.auth.resolver

import org.sopt.common.exception.BusinessException
import org.sopt.common.exception.ErrorCode
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserIdArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.hasParameterAnnotation(UserId::class.java) && parameter.parameterType == Long::class.java

    override fun resolveArgument(
        parameter: MethodParameter, mavContainer: ModelAndViewContainer,
        webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory
    ): Any {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw BusinessException(ErrorCode.INVALID_AUTHENTICATION)

        return authentication.name.toLongOrNull()
            ?: throw BusinessException(ErrorCode.INVALID_AUTHENTICATION)
    }
}
