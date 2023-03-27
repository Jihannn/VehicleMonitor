package com.jihan.monitor.factory;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.fwk.sdk.hvac.HvacManager;
import com.jihan.monitor.model.HvacRepository;


public class AppInjection {

    private final static AppViewModelFactory mViewModelFactory = new AppViewModelFactory();

    public static <T extends ViewModel> T getViewModel(ViewModelStoreOwner store, Class<T> clazz) {
        return new ViewModelProvider(store, mViewModelFactory).get(clazz);
    }

    public static AppViewModelFactory getViewModelFactory() {
        return mViewModelFactory;
    }

    /**
     * 受保护的权限,除了ViewModel，其它模块不应该需要Model层的实例
     *
     */
    protected static HvacRepository getHvacRepository() {
        return new HvacRepository(getHvacManager());
    }

    public static HvacManager getHvacManager() {
        return HvacManager.getInstance();
    }

}