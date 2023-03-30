package com.jihan.monitor.ui;


import com.jihan.lib_common.base.BaseMvvmActivity;
import com.jihan.monitor.BR;
import com.jihan.monitor.R;
import com.jihan.monitor.databinding.ActivityHvacBinding;
import com.jihan.monitor.factory.AppInjection;

public class HvacActivity extends BaseMvvmActivity<HvacViewModel, ActivityHvacBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hvac;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected Object getViewModelOrFactory() {
        return AppInjection.getViewModelFactory();
    }

    @Override
    protected int getViewModelVariable() {
        return BR.viewModel2;
    }

    @Override
    protected void initObservable(HvacViewModel viewModel) {

    }

    @Override
    protected void loadData(HvacViewModel viewModel) {
        mViewModel.requestTemperature();
    }
}
