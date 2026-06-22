package org.sopt.auth

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class TokenBlacklistService(
    private val redisTemplate: StringRedisTemplate
) {
    fun blacklist(
        accessToken: String,
        remainingSeconds: Long
    ) {
        redisTemplate.opsForValue().set(
            "blacklist:$accessToken",
            "logout",
            Duration.ofSeconds(remainingSeconds)
        )
    }

    fun isBlacklisted(accessToken: String): Boolean =
        redisTemplate.hasKey("blacklist:$accessToken") == true
}
