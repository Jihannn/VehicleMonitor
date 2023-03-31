package com.jihan.monitor.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.jihan.lib_common.utils.AppExecutors;
import com.jihan.monitor.model.HvacRepository;
import com.jihan.monitor.model.LoginRepository;
import com.jihan.monitor.model.VehicleRepository;
import com.jihan.monitor.ui.HvacViewModel;
import com.jihan.monitor.ui.LoginViewModel;
import com.jihan.monitor.ui.MainViewModel;

import java.lang.reflect.InvocationTargetException;

// default 权限，不对外部公开此类
class AppViewModelFactory implements ViewModelProvider.Factory {

    // 创建 viewModel 实例
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            if (modelClass == HvacViewModel.class) {
                return modelClass.getConstructor(HvacRepository.class, AppExecutors.class)
                        .newInstance(AppInjection.getHvacRepository(), AppExecutors.get());
            } else if (modelClass == MainViewModel.class) {
                return modelClass.getConstructor(VehicleRepository.class, AppExecutors.class)
                        .newInstance(AppInjection.getVehicleRepository(), AppExecutors.get());
            }else if (modelClass == LoginViewModel.class) {
                return modelClass.getConstructor(LoginRepository.class, AppExecutors.class)
                        .newInstance(AppInjection.getLoginRepository(), AppExecutors.get());
            } else {
                throw new RuntimeException(modelClass.getSimpleName() + "create failed");
            }
        } catch (NoSuchMethodException | IllegalAccessException
                 | InstantiationException | InvocationTargetException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }
}
