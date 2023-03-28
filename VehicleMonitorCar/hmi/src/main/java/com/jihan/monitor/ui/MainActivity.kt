package com.jihan.monitor.ui

import android.os.Bundle
import android.os.IBinder
import com.jihan.lib_common.base.BaseActivity2
import com.jihan.lib_common.utils.LogUtils
import com.jihan.monitor.R
import com.jihan.monitor.databinding.ActivityMainBinding
import com.jihan.monitor.sdk.VehicleCallback
import com.jihan.monitor.sdk.VehicleManager
import com.jihan.monitor.service.IVehicleCallback
import com.jihan.monitor.service.model.Vehicle


class MainActivity : BaseActivity2<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {
    private val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VehicleManager.getInstance().registerCallback { speed ->
            LogUtils.logI(
                TAG,
                "[onSpeedChanged]$speed"
            )
        }
    }
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.btnStatus.setOnClickListener{
            val vehicle = Vehicle()
            VehicleManager.getInstance().getVehicleStatus(vehicle)
            LogUtils.logI(TAG,"[initView] $vehicle")
        }
    }
}