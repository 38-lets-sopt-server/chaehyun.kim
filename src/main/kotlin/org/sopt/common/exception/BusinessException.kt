package org.sopt.common.exception

open class BusinessException @JvmOverloads constructor(
    val errorCode: ErrorCode,
    message: String = errorCode.message
) : RuntimeException(message)
