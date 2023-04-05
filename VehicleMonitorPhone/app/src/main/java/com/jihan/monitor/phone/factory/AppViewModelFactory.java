package com.jihan.monitor.phone.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jihan.monitor.lib_common.model.BaseRepository;
import com.jihan.monitor.lib_common.utils.AppExecutors;
import com.jihan.monitor.phone.dapter.VehicleAdapter;
import com.jihan.monitor.phone.model.UserRepository;
import com.jihan.monitor.phone.model.VehicleRepository;
import com.jihan.monitor.phone.ui.CarDetailViewModel;
import com.jihan.monitor.phone.ui.CarViewModel;
import com.jihan.monitor.phone.ui.LoginViewModel;
import com.jihan.monitor.phone.ui.MainViewModel;
import com.jihan.monitor.phone.ui.MineViewModel;

import java.lang.reflect.InvocationTargetException;

// default 权限，不对外部公开此类
class AppViewModelFactory implements ViewModelProvider.Factory {

    // 创建 viewModel 实例
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            if (modelClass == LoginViewModel.class) {
                return modelClass.getConstructor(UserRepository.class)
                        .newInstance(AppInjection.getLoginRepository
                                ());
            }else if (modelClass == CarViewModel.class) {
                return modelClass.getConstructor(VehicleRepository.class)
                        .newInstance(AppInjection.getVehicleRepository());
            } else if (modelClass == MainViewModel.class) {
                return modelClass.getConstructor()
                        .newInstance();
            }  else if (modelClass == CarDetailViewModel.class) {
                return modelClass.getConstructor(VehicleRepository.class, AppExecutors.class)
                        .newInstance(AppInjection.getVehicleRepository(),AppExecutors.get());
            } else if (modelClass == MineViewModel.class) {
                return modelClass.getConstructor()
                        .newInstance();
            }  else {
                throw new RuntimeException(modelClass.getSimpleName() + "create failed");
            }
        } catch (NoSuchMethodException | IllegalAccessException
                 | InstantiationException | InvocationTargetException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }
}
