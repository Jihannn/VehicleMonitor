package com.jihan.monitor.lib_common.viewmodel;

import androidx.lifecycle.ViewModel;

import com.jihan.monitor.lib_common.model.BaseRepository;

public abstract class BaseViewModel<M extends BaseRepository> extends ViewModel {

    protected M mRepository;

    public BaseViewModel(){

    }

    public BaseViewModel(M repository) {
        mRepository = repository;
    }

    public M getRepository() {
        return mRepository;
    }
}