package com.jihan.monitor.factory;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.fwk.sdk.hvac.HvacManager;
import com.jihan.monitor.model.HvacRepository;
import com.jihan.monitor.model.LoginRepository;
import com.jihan.monitor.model.VehicleRepository;
import com.jihan.monitor.sdk.VehicleManager;

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
    protected static HvacRepository getHvacRepository() {
        return new HvacRepository(getHvacManager());
    }

    public static HvacManager getHvacManager() {
        return HvacManager.getInstance();
    }

    protected static VehicleRepository getVehicleRepository() {
        return new VehicleRepository(getVehicleManager());
    }

    protected static LoginRepository getLoginRepository() {
        return new LoginRepository(getVehicleManager());
    }

    public static VehicleManager getVehicleManager() {
        return VehicleManager.getInstance();
    }

}