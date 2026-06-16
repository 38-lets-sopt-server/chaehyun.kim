package org.sopt.common.auth;

import java.io.IOException;
import java.util.Collections;

import org.sopt.common.exception.ErrorCode;
import org.sopt.common.response.CustomAPIResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final TokenBlacklistService tokenBlacklistService;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring("Bearer ".length()).trim();
			try {
				if (tokenBlacklistService.isBlacklisted(token)) {
					sendErrorResponse(response, ErrorCode.BLACKLISTED_TOKEN);
					return;
				}

				Long memberId = jwtService.verifyAndGetUserId(token);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					String.valueOf(memberId), null, Collections.emptyList());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
				request.setAttribute("accessToken", token);
			} catch (TokenExpiredException e) {
				sendErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
				return;
			} catch (JWTVerificationException e) {
				sendErrorResponse(response, ErrorCode.INVALID_TOKEN);
				return;
			} catch (IllegalArgumentException e) {
				sendErrorResponse(response, ErrorCode.INVALID_TOKEN);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
		response.setStatus(errorCode.getStatus().value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(
			objectMapper.writeValueAsString(
				CustomAPIResponse.createFail(errorCode.getCode(), errorCode.getMessage())
			)
		);
	}
}
