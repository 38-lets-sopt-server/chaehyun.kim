package org.sopt.common.response

data class CustomAPIResponse<T>(
    val status: String,
    val message: String,
    val data: T
) {
    companion object {
        // 성공
        fun <T> createSuccess(
            status: SuccessStatus,
            data: T
        ): CustomAPIResponse<T> =
            CustomAPIResponse(
                status.status.value().toString(),
                status.message,
                data
            )

        // 실패
        fun createFail(
            status: String,
            message: String
        ): CustomAPIResponse<Any?> =
            CustomAPIResponse(
                status,
                message,
                null
            )

        fun <T> createFail(
            status: String,
            message: String,
            data: T
        ): CustomAPIResponse<T> =
            CustomAPIResponse(
                status,
                message,
                data
            )
    }
}
