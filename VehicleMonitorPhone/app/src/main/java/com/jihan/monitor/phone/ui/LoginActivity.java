package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import android.view.View;

import com.jihan.monitor.lib_common.base.BaseMvvmActivity;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.BR;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.databinding.ActivityLoginBinding;
import com.jihan.monitor.phone.factory.AppInjection;

public class LoginActivity extends BaseMvvmActivity<LoginViewModel,ActivityLoginBinding> {

    private static final String TAG = TAG_PHONE + LoginActivity.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mBinding.btnLogin.setOnClickListener(v -> {
            LogUtils.logI(TAG,"login");
        });
        mBinding.tvRegister.setOnClickListener(v ->{
            LogUtils.logI(TAG,"register");
            new RegisterDialog(this,mViewModel).show();
        });
    }

    @Override
    protected Object getViewModelOrFactory() {
        return AppInjection.getViewModelFactory();
    }

    @Override
    protected int getViewModelVariable() {
        return BR.viewModel;
    }

    @Override
    protected void initObservable(LoginViewModel viewModel) {

    }

    @Override
    protected void loadData(LoginViewModel viewModel) {

    }
}
