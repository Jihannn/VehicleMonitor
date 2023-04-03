package com.jihan.monitor.phone.ui;

import androidx.lifecycle.MutableLiveData;

import com.jihan.monitor.lib_common.model.BaseRepository;
import com.jihan.monitor.lib_common.viewmodel.BaseViewModel;

public class MainViewModel extends BaseViewModel<BaseRepository> {

    public MutableLiveData<Integer> currentPositionResIdLiveData = new MutableLiveData<>();

    public MainViewModel(){
    }

    public void switchTab(Integer resId){
        currentPositionResIdLiveData.setValue(resId);
    }
}
