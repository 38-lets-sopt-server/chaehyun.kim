package org.sopt.common.auth;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

	private final StringRedisTemplate redisTemplate;

	public void blacklist(String accessToken, long remainingSeconds) {
		redisTemplate.opsForValue()
			.set("blacklist:" + accessToken, "logout", Duration.ofSeconds(remainingSeconds));

		// 바로 조회해서 확인
		String value = redisTemplate.opsForValue().get("blacklist:" + accessToken);
		System.out.println("Redis 저장 확인: " + value);
	}

	public boolean isBlacklisted(String accessToken) {
		return Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + accessToken));
	}
}
