package com.jihan.monitor.phone.dapter;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.databinding.ItemVehicleBinding;
import com.jihan.monitor.phone.databinding.ItemWarningMessageBinding;
import com.jihan.monitor.phone.model.Vehicle;
import com.jihan.monitor.phone.model.WarningMessage;
import com.jihan.monitor.phone.ui.CarDetailActivity;

public class WarningMessageAdapter extends BaseQuickAdapter<WarningMessage, BaseDataBindingHolder<ItemWarningMessageBinding>> implements LoadMoreModule {

    private static final String TAG = TAG_PHONE + WarningMessageAdapter.class.getSimpleName();

    public WarningMessageAdapter(int layoutResId) {
        super(layoutResId);
        LogUtils.logI(TAG,"[init]");
        setAnimationWithDefault(AnimationType.ScaleIn);
    }

    @Override
    protected void convert(@NonNull BaseDataBindingHolder<ItemWarningMessageBinding> holder, WarningMessage warningMessage) {
        LogUtils.logI(TAG,"[convert]"+warningMessage.toString());
        ItemWarningMessageBinding dataBinding = holder.getDataBinding();
        if(dataBinding != null){
            dataBinding.setWarningMessage(warningMessage);
            dataBinding.executePendingBindings();
            dataBinding.layoutRelative.setOnClickListener(v -> {
                LogUtils.logI(TAG,"[convert]"+warningMessage.toString());
            });
        }
    }

    @NonNull
    @Override
    public BaseLoadMoreModule addLoadMoreModule(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        return new BaseLoadMoreModule(baseQuickAdapter);
    }
}
