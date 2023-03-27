package com.jihan.lib_common.viewmodel;

import androidx.lifecycle.ViewModel;

import com.jihan.lib_common.model.BaseRepository;

public abstract class BaseViewModel<M extends BaseRepository> extends ViewModel {

    protected M mRepository;

    public BaseViewModel(M repository) {
        mRepository = repository;
    }

    public M getRepository() {
        return mRepository;
    }
}