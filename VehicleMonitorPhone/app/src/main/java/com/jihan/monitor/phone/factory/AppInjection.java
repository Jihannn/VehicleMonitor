package com.jihan.monitor.phone.factory;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.jihan.monitor.phone.model.UserRepository;
import com.jihan.monitor.phone.model.VehicleRepository;


/**
 * 创建单例、ViewModel、Repository的统一出口
 */
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
    protected static UserRepository getLoginRepository() {
        return new UserRepository();
    }

    protected static VehicleRepository getVehicleRepository() {
        return new VehicleRepository();
    }
}