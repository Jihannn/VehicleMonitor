package com.jihan.monitor.lib_common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;


import com.jihan.monitor.lib_common.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseMvvmActivity<VM extends BaseViewModel, V extends ViewDataBinding> extends BaseBindingActivity<V> {

    protected VM mViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initViewModel();
        super.onCreate(savedInstanceState);
        if (getViewModelVariable() != 0) {
            mBinding.setVariable(getViewModelVariable(), mViewModel);
        }
        mBinding.executePendingBindings();
        initObservable(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData(mViewModel);
    }

    private void initViewModel() {
        Class<VM> modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class<VM>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            modelClass = (Class<VM>) BaseViewModel.class;
        }
        Object object = getViewModelOrFactory();
        if (object instanceof BaseViewModel){
            mViewModel = (VM) object;
        }else if (object instanceof ViewModelProvider.Factory){
            mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) object)
                    .get(modelClass);
        }else {
            mViewModel = new ViewModelProvider(this,
                    (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(modelClass);
        }
    }

    /**
     * 返回ViewModel的实例或ViewModelFactory实例
     */
    protected abstract Object getViewModelOrFactory();
    /**
     * 返回XML中ViewModel的BR-ID
     */
    protected abstract int getViewModelVariable();

    protected abstract void initObservable(VM viewModel);

    protected abstract void loadData(VM viewModel);

    protected VM getViewModel() {
        return mViewModel;
    }
}