package com.jihan.monitor.lib_common.model

data class BaseResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)
