package com.jihan.monitor.lib_common.viewmodel;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.jihan.monitor.lib_common.model.BaseRepository;

public abstract class BaseAndroidViewModel<M extends BaseRepository> extends AndroidViewModel {

    protected M mRepository;

    public BaseAndroidViewModel(Application application, @Nullable M repository) {
        super(application);
        mRepository = repository;
    }

    public M getRepository() {
        return mRepository;
    }
}
