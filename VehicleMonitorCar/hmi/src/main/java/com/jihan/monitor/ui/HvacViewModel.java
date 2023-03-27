package com.jihan.monitor.ui;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.jihan.lib_common.base.utils.AppExecutors;
import com.jihan.lib_common.base.utils.LogUtils;
import com.jihan.lib_common.base.viewmodel.BaseViewModel;
import com.jihan.monitor.CarApp;
import com.jihan.monitor.model.HvacCallback;
import com.jihan.monitor.model.HvacRepository;


public class HvacViewModel extends BaseViewModel<HvacRepository> {

    private static final String TAG = CarApp.TAG_HVAC + HvacViewModel.class.getSimpleName();

    private final HvacRepository mRepository;
    // 线程池框架。某些场景，ViewModel访问Repository中的方法可能会需要切换到子线程。
    private final AppExecutors mAppExecutors;
    private MutableLiveData<String> mTempLive;

    private final HvacCallback mHvacCallback = new HvacCallback() {
        @Override
        public void onTemperatureChanged(String temp) {
            LogUtils.logI(TAG, "[onTemperatureChanged] " + temp);
            getTempLive().postValue(temp);
        }
    };

    public HvacViewModel(HvacRepository repository, AppExecutors executors) {
        super(repository);
        mRepository = repository;
        mAppExecutors = executors;
        mRepository.setHvacListener(mHvacCallback);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository.removeHvacListener(mHvacCallback);
        mRepository.release();
    }

    /**
     * 请求页面数据
     */
    public void requestTemperature() {
        mRepository.requestTemperature();
    }

    /**
     * 将温度数据设定到Service中
     */
    public void setTemperature(View view) {
        mRepository.setTemperature(getTempLive().getValue());
    }

    public MutableLiveData<String> getTempLive() {
        if (mTempLive == null) {
            mTempLive = new MutableLiveData<>();
        }
        return mTempLive;
    }
}