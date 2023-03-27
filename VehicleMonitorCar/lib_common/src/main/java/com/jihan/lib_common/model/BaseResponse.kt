package com.jihan.lib_common.model

data class BaseResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)
