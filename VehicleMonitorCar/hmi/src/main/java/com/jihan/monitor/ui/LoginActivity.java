package com.jihan.monitor.ui;

import static com.jihan.monitor.CarApp.TAG_HMI;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.jihan.lib_common.base.BaseMvvmActivity;
import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.R;
import com.jihan.monitor.databinding.ActivityLoginBinding;
import com.jihan.monitor.factory.AppInjection;
import com.jihan.monitor.model.UserManager;
import com.jihan.monitor.sdk.Constants;

public class LoginActivity extends BaseMvvmActivity<LoginViewModel, ActivityLoginBinding> {

    private static final String TAG = TAG_HMI + LoginActivity.class.getSimpleName();

    public static class Launcher{
        public static void newInstance(Context context){
            context.startActivity(new Intent(context,LoginActivity.class));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        if(UserManager.isLogin()){
            MainActivity.Launcher.newInstance(LoginActivity.this);
            finish();
        }
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable username = mBinding.editName.getText();
                Editable pwd = mBinding.editPassword.getText();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "用户名密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    mViewModel.requestLogin(username.toString(), pwd.toString());
                }
            }
        });
    }

    @Override
    protected Object getViewModelOrFactory() {
        return AppInjection.getViewModelFactory();
    }

    @Override
    protected int getViewModelVariable() {
        return 0;
    }

    @Override
    protected void initObservable(LoginViewModel viewModel) {
        mViewModel.getLoginResultLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer result) {
                LogUtils.logI(TAG, "[initObservable]:onChanged" + result);
                if(result == Constants.CODE_SERVICE_NOT_CONNECT){
                    Toast.makeText(LoginActivity.this, "与服务失去连接", Toast.LENGTH_LONG).show();
                }else if(result == Constants.CODE_SUCCESS){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    MainActivity.Launcher.newInstance(LoginActivity.this);
                    UserManager.saveUser(mBinding.editName.getText().toString());
                    finish();
                }
            }
        });
    }

    @Override
    protected void loadData(LoginViewModel viewModel) {
    }
}
