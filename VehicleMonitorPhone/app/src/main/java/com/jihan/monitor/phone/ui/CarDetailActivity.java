package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import com.jihan.monitor.lib_common.base.BaseMvvmActivity;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.BR;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.databinding.ActivityCarDetailBinding;
import com.jihan.monitor.phone.factory.AppInjection;
import com.jihan.monitor.phone.model.Vehicle;

public class CarDetailActivity extends BaseMvvmActivity<CarDetailViewModel, ActivityCarDetailBinding> {
    private static final String TAG = TAG_PHONE + CarDetailActivity.class.getSimpleName();
    private Vehicle mVehicle;
    public static class Launcher{
        private static final String PARCEL_VEHICLE = "vehicle";
        public static void launch(Context context, Vehicle vehicle){
            Intent intent = new Intent(context, CarDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(PARCEL_VEHICLE,vehicle);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_detail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                mVehicle = bundle.getParcelable(Launcher.PARCEL_VEHICLE);
            }
        }
        mBinding.tbHome.setTitle(R.string.car_status);
        mBinding.tbHome.setTitleTextColor(Color.BLACK);
        mBinding.tbHome.setNavigationIcon(R.drawable.ic_back);
        mBinding.tbHome.setNavigationOnClickListener(v -> CarDetailActivity.super.onBackPressed());
        setToolbarTitleCenter(mBinding.tbHome);
        if(mVehicle != null){
            LogUtils.logI(TAG,"[loadData]"+mVehicle.toString());
            mViewModel.getLatestVehicleStatus(mVehicle.getDevice_id());
        }
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
    protected void initObservable(CarDetailViewModel viewModel) {
        mViewModel.vehicleStatusLiveData.observe(this, vehicleStatus -> LogUtils.logI(TAG,"[onChanged]"+vehicleStatus.toString()));
    }

    @Override
    protected void loadData(CarDetailViewModel viewModel) {
    }

    private void setToolbarTitleCenter(Toolbar toolbar) {
        String title = "title";
        CharSequence originalTitle = toolbar.getTitle();
        toolbar.setTitle(title);
        int childCount = toolbar.getChildCount();
        for (int i=0;i< childCount;i++) {
            View view = toolbar.getChildAt(i);
            if(view instanceof TextView){
                if(title == ((TextView) view).getText()){
                    ((TextView) view).setGravity(Gravity.CENTER);
                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    view.setLayoutParams(params);
                }
            }
            toolbar.setTitle(originalTitle);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.closeClientConnection();
    }
}
