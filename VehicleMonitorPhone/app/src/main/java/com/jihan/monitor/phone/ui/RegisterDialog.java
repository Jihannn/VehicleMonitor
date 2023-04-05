package com.jihan.monitor.phone.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Observer;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.databinding.DialogRegisterBinding;
import com.jihan.monitor.phone.model.Constants;

public class RegisterDialog {

    private final LoginActivity mLoginActivity;
    private final LoginViewModel mLoginViewModel;
    private final DialogRegisterBinding mDialogRegisterBinding;
    private final AlertDialog mRegisterDialog;

    public ObservableField<String> userNameObservable = new ObservableField<>();
    public ObservableField<String> passwordObservable = new ObservableField<>();
    public ObservableField<String> confirmPasswordObservable = new ObservableField<>();
    public ObservableBoolean loginEnable = new ObservableBoolean(userNameObservable,passwordObservable,confirmPasswordObservable){
        @Override
        public boolean get() {
            return !TextUtils.isEmpty(userNameObservable.get()) && !TextUtils.isEmpty(passwordObservable.get()) && !TextUtils.isEmpty(confirmPasswordObservable.get()) ;
        }
    };


    public RegisterDialog(LoginActivity activity,LoginViewModel viewModel){
        this.mLoginActivity = activity;
        this.mLoginViewModel = viewModel;
        this.mDialogRegisterBinding = DataBindingUtil.inflate(LayoutInflater.from(mLoginActivity), R.layout.dialog_register,null,false);
        mDialogRegisterBinding.setLifecycleOwner(activity);
        this.mRegisterDialog = new MaterialAlertDialogBuilder(mLoginActivity).setView(mDialogRegisterBinding.getRoot()).setCancelable(true).create();
        init();
    }

    private void init(){
        mDialogRegisterBinding.setDialog(this);
        mDialogRegisterBinding.registerButton.setOnClickListener(v -> {
            String username = userNameObservable.get().trim().toString();
            String password = passwordObservable.get().trim().toString();
            String confirmPassword = confirmPasswordObservable.get().trim().toString();
            if(checkRegisterStatus(username,password,confirmPassword)){
                mLoginViewModel.register(username, password);
                mDialogRegisterBinding.Loading.setVisibility(View.VISIBLE);
            }
        });
        mLoginViewModel.registerLiveData.observe(mLoginActivity, result -> {
            if(result == Constants.CODE_SUCCESS){
                mRegisterDialog.dismiss();
                Toast.makeText(mLoginActivity, mLoginActivity.getString(R.string.register_success), Toast.LENGTH_SHORT).show();;
                mDialogRegisterBinding.Loading.setVisibility(View.GONE);
            }else{
                mRegisterDialog.dismiss();
                Toast.makeText(mLoginActivity, mLoginActivity.getString(R.string.register_failure), Toast.LENGTH_SHORT).show();;
                mDialogRegisterBinding.Loading.setVisibility(View.GONE);
            }
        });
    }

    public void show(){
        mRegisterDialog.show();
    }

    private boolean checkRegisterStatus(String username,String password,String confirmPassword){
        if (username.length() < 3) {
            mDialogRegisterBinding.userNameInputLayout.setError("用户名最少3位");
            return false;
        }
        if (password.length() < 6) {
            mDialogRegisterBinding.passwordInputLayout.setError("密码至少 6 位");
            return false;
        }
        if (confirmPassword.length() < 6) {
            mDialogRegisterBinding.confirmPasswordInputLayout.setError("密码至少 6 位");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            mDialogRegisterBinding.confirmPasswordInputLayout.setError("确认密码与密码不符");
            return false;
        }
        return true;
    }
}
