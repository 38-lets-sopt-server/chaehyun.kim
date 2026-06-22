package org.sopt.auth.filter

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.sopt.auth.service.JwtService
import org.sopt.auth.service.TokenBlacklistService
import org.sopt.common.exception.ErrorCode
import org.sopt.common.response.CustomAPIResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val tokenBlacklistService: TokenBlacklistService,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header?.startsWith("Bearer ") == true) {
            val token = header.substring("Bearer ".length).trim()
            try {
                if (tokenBlacklistService.isBlacklisted(token)) {
                    sendErrorResponse(response, ErrorCode.BLACKLISTED_TOKEN)
                    return
                }

                val memberId = jwtService.verifyAndGetUserId(token)
                val authentication = UsernamePasswordAuthenticationToken(
                    memberId.toString(), null, emptyList()
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
                request.setAttribute("accessToken", token)
            } catch (e: TokenExpiredException) {
                sendErrorResponse(response, ErrorCode.EXPIRED_TOKEN)
                return
            } catch (e: JWTVerificationException) {
                sendErrorResponse(response, ErrorCode.INVALID_TOKEN)
                return
            } catch (e: IllegalArgumentException) {
                sendErrorResponse(response, ErrorCode.INVALID_TOKEN)
                return
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        response.status = errorCode.status.value()
        response.contentType = "application/json;charset=UTF-8"
        response.writer.write(
            objectMapper.writeValueAsString(
                CustomAPIResponse.createFail<Nothing>(errorCode.code, errorCode.message)
            )
        )
    }
}
