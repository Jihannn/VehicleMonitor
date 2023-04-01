package com.jihan.monitor.lib_common.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

open class MyApplication : Application(), ViewModelStoreOwner {
    companion object {
        lateinit var appContext: Context
    }

    private lateinit var mAppViewModelStore: ViewModelStore

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        mAppViewModelStore = ViewModelStore()
    }
}