package com.jihan.lib_common.base.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel2 : ViewModel() {
//    val exception = MutableLiveData<Exception>()

//    val errorResponse = MutableLiveData<BaseResponse<*>>()

    abstract fun start()
}