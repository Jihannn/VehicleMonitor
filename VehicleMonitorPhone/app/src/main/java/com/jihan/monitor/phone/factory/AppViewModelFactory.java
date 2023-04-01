package com.jihan.monitor.phone.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jihan.monitor.phone.network.LoginRepository;
import com.jihan.monitor.phone.ui.LoginViewModel;

import java.lang.reflect.InvocationTargetException;

// default 权限，不对外部公开此类
class AppViewModelFactory implements ViewModelProvider.Factory {

    // 创建 viewModel 实例
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            if (modelClass == LoginViewModel.class) {
                return modelClass.getConstructor(LoginRepository.class)
                        .newInstance(AppInjection.getLoginRepository());
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
