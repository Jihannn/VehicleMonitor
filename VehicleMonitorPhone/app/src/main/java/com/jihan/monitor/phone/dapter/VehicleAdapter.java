package com.jihan.monitor.phone.dapter;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.BaseUpFetchModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.databinding.ItemVehicleBinding;
import com.jihan.monitor.phone.model.Vehicle;
import com.jihan.monitor.phone.ui.CarDetailActivity;

public class VehicleAdapter extends BaseQuickAdapter<Vehicle, BaseDataBindingHolder<ItemVehicleBinding>> implements LoadMoreModule{

    private static final String TAG = TAG_PHONE + VehicleAdapter.class.getSimpleName();

    public VehicleAdapter(int layoutResId) {
        super(layoutResId);
        LogUtils.logI(TAG,"[init]");
        setAnimationWithDefault(AnimationType.ScaleIn);
    }

    @Override
    protected void convert(@NonNull BaseDataBindingHolder<ItemVehicleBinding> holder, Vehicle vehicle) {
        LogUtils.logI(TAG,"[convert]"+vehicle.toString());
        ItemVehicleBinding dataBinding = holder.getDataBinding();
        if(dataBinding != null){
            dataBinding.setVehicle(vehicle);
            dataBinding.executePendingBindings();
            dataBinding.layoutRelative.setOnClickListener(v -> {
                LogUtils.logI(TAG,"[jump]:"+vehicle.toString());
                CarDetailActivity.Launcher.launch(getContext(),vehicle);
            });
        }
    }

    @NonNull
    @Override
    public BaseLoadMoreModule addLoadMoreModule(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        return new BaseLoadMoreModule(baseQuickAdapter);
    }
}
