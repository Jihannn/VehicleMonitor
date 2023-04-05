package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.jihan.monitor.lib_common.base.BaseMvvmActivity;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.BR;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.databinding.ActivityLoginBinding;
import com.jihan.monitor.phone.factory.AppInjection;
import com.jihan.monitor.phone.model.Constants;
import com.jihan.monitor.phone.model.UserManager;
import com.jihan.monitor.phone.utils.SPUtils;

public class LoginActivity extends BaseMvvmActivity<LoginViewModel,ActivityLoginBinding> {

    private static final String TAG = TAG_PHONE + LoginActivity.class.getSimpleName();

    public static class LAUNCHER{
        public static void launch(Context context){
            context.startActivity(new Intent(context,LoginActivity.class));
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        LogUtils.logI(TAG,"[token]:"+UserManager.getUser());
        if(UserManager.isLogin()){
            LogUtils.logI(TAG,"[initView]-已经登录");
            MainActivity.LAUNCHER.launch(LoginActivity.this);
            finish();
        }
        mBinding.btnLogin.setOnClickListener(v -> {
            LogUtils.logI(TAG,"login");
            Editable usernameEdit = mBinding.editName.getText();
            Editable passwordEdit = mBinding.editPassword.getText();
            if(usernameEdit == null || passwordEdit == null){
                Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            }else{
                mBinding.loginLoading.setVisibility(View.VISIBLE);
                mViewModel.login(usernameEdit.toString().trim(),passwordEdit.toString().trim());
            }
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
        mViewModel.loginLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer result) {
                mBinding.loginLoading.setVisibility(View.GONE);
                if (result == Constants.CODE_SUCCESS) {
                    LogUtils.logI(TAG,"登录成功");
                    MainActivity.LAUNCHER.launch(LoginActivity.this);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    LogUtils.logI(TAG,"登录失败");
                }
            }
        });
    }

    @Override
    protected void loadData(LoginViewModel viewModel) {

    }
}
