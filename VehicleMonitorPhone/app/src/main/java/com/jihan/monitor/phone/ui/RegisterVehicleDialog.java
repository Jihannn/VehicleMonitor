package com.jihan.monitor.phone.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.databinding.DialogRegisterVehicleBinding;
import com.jihan.monitor.phone.model.Constants;

public class RegisterVehicleDialog {

    private final CarFragment mCarFragment;
    private final CarViewModel mCarViewModel;
    private final DialogRegisterVehicleBinding mDialogVehicleRegisterBinding;
    private final AlertDialog mRegisterDialog;

    public ObservableField<String> plateNumberObservable = new ObservableField<>();
    public ObservableField<String> brandObservable = new ObservableField<>();
    public ObservableField<String> modelObservable = new ObservableField<>();
    public ObservableField<String> devicesIdObservable = new ObservableField<>();

    public ObservableBoolean registerEnable = new ObservableBoolean(plateNumberObservable,brandObservable,modelObservable,devicesIdObservable){
        @Override
        public boolean get() {
            return !TextUtils.isEmpty(plateNumberObservable.get()) && !TextUtils.isEmpty(brandObservable.get())
                    && !TextUtils.isEmpty(modelObservable.get()) && !TextUtils.isEmpty(devicesIdObservable.get());
        }
    };


    public RegisterVehicleDialog(CarFragment fragment,CarViewModel viewModel){
        this.mCarFragment = fragment;
        this.mCarViewModel = viewModel;
        this.mDialogVehicleRegisterBinding = DataBindingUtil.inflate(LayoutInflater.from(mCarFragment.getContext()), R.layout.dialog_register_vehicle,null,false);
        mDialogVehicleRegisterBinding.setLifecycleOwner(fragment);
        this.mRegisterDialog = new MaterialAlertDialogBuilder(mCarFragment.getContext()).setView(mDialogVehicleRegisterBinding.getRoot()).setCancelable(true).create();
        init();
    }

    private void init(){
        mDialogVehicleRegisterBinding.setDialog(this);
        mDialogVehicleRegisterBinding.registerButton.setOnClickListener(v -> {
            String plateNumber = plateNumberObservable.get().trim().toString();
            String brand = brandObservable.get().trim().toString();
            String model = modelObservable.get().trim().toString();
            String deviceId = devicesIdObservable.get().trim().toString();
            if(checkRegisterStatus(plateNumber,brand,model,deviceId)){
                mCarViewModel.register(plateNumber,brand,model,deviceId);
                mDialogVehicleRegisterBinding.Loading.setVisibility(View.VISIBLE);
            }
        });
        mCarViewModel.registerLiveData.observe(mCarFragment, result -> {
            if(result == Constants.CODE_SUCCESS){
                mRegisterDialog.dismiss();
                Toast.makeText(mCarFragment.getContext(), mCarFragment.getString(R.string.vehicle_register_success), Toast.LENGTH_SHORT).show();;
                mDialogVehicleRegisterBinding.Loading.setVisibility(View.GONE);
            }else{
                mRegisterDialog.dismiss();
                Toast.makeText(mCarFragment.getContext(), mCarFragment.getString(R.string.vehicle_register_failure), Toast.LENGTH_SHORT).show();;
                mDialogVehicleRegisterBinding.Loading.setVisibility(View.GONE);
            }
        });
    }

    public void show(){
        mRegisterDialog.show();
    }

    private boolean checkRegisterStatus(String plate_number,String brand,String model,String productionYear){
        if (plate_number.length() < 8) {
            mDialogVehicleRegisterBinding.plateNumberInputLayout.setError("车牌号最少8位");
            return false;
        }
        if (brand.length() < 1) {
            mDialogVehicleRegisterBinding.brandInputLayout.setError("请输入正确的品牌");
            return false;
        }
        if (model.length() < 2) {
            mDialogVehicleRegisterBinding.modelInputLayout.setError("请输入正确的车型");
            return false;
        }
        if (productionYear.length() < 4) {
            mDialogVehicleRegisterBinding.modelYearInputLayout.setError("请输入正确的出厂年份");
            return false;
        }
        return true;
    }
}
