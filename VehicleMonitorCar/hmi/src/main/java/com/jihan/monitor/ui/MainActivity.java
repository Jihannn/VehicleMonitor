package com.jihan.monitor.ui;

import static com.jihan.monitor.CarApp.TAG_HMI;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;


import androidx.lifecycle.MutableLiveData;

import com.jihan.lib_common.base.BaseMvvmActivity;
import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.BR;
import com.jihan.monitor.R;
import com.jihan.monitor.databinding.ActivityMainBinding;
import com.jihan.monitor.factory.AppInjection;
import com.jihan.monitor.model.UserManager;
import com.jihan.monitor.sdk.Constants;
import com.jihan.monitor.sdk.VehicleCallback;
import com.jihan.monitor.service.model.Vehicle;

public class MainActivity extends BaseMvvmActivity<MainViewModel, ActivityMainBinding> {

    private static final String TAG = TAG_HMI + MainActivity.class.getSimpleName();
    public static class Launcher{
        public static void newInstance(Context context){
            context.startActivity(new Intent(context,MainActivity.class));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mBinding.btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });
        mBinding.btnWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int warning = mViewModel.warning();
                if(warning == Constants.CODE_SERVICE_NOT_CONNECT){
                    Toast.makeText(MainActivity.this, "与服务断开连接，非常抱歉，请保重！", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "发送报警信号成功，请耐心等待！", Toast.LENGTH_LONG).show();
                }
            }
        });
        mBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.logout();
                LoginActivity.Launcher.newInstance(MainActivity.this);
                finish();
            }
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
    protected void initObservable(MainViewModel viewModel) {
        mViewModel.registerStatusCallback(new VehicleCallback() {
            @Override
            public void onStatusChanged(float speed, float fuelLevel) {
                LogUtils.logI(TAG,"[initObservable]:"+speed+"fuelLevel:"+fuelLevel);
                MutableLiveData<Vehicle> vehicleLiveData = mViewModel.getVehicleLiveData();
                Vehicle vehicle = vehicleLiveData.getValue();
                if(vehicle != null){
                    vehicle.setSpeed(speed);
                    vehicle.setFuelLevel(fuelLevel);
                    vehicleLiveData.setValue(vehicle);
                }
            }
        });
    }

    @Override
    protected void loadData(MainViewModel viewModel) {
        refreshData();
    }

    private void refreshData(){
        mViewModel.requestVehicleData();
    }
}
