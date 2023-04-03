package com.jihan.monitor.phone.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.jihan.monitor.lib_common.base.BaseBindingActivity;
import com.jihan.monitor.lib_common.base.BaseMvvmActivity;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.databinding.ActivityMainBinding;
import com.jihan.monitor.phone.factory.AppInjection;

public class MainActivity extends BaseMvvmActivity<MainViewModel,ActivityMainBinding> {

    public static class LAUNCHER{
        public static void launch(Context context){
            context.startActivity(new Intent(context,MainActivity.class));
        }
    }

    private CarFragment mCarFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
    protected void initObservable(MainViewModel viewModel) {

    }

    @Override
    protected void loadData(MainViewModel viewModel) {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mCarFragment = new CarFragment();
        mMineFragment = new MineFragment();
        mCurrentFragment = mCarFragment;
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container_main,mCarFragment,"car").commitAllowingStateLoss();
        }else{
            Integer tab = mViewModel.currentPositionResIdLiveData.getValue();
            if(tab != null){
                mBinding.navBottom.setSelectedItemId(tab);
                onNavBarItemSelected(tab);
            }
        }
        mBinding.navBottom.setOnItemSelectedListener(item -> {
            onNavBarItemSelected(item.getItemId());
            return true;
        });
    }

    private void onNavBarItemSelected(Integer itemResId){
        mViewModel.switchTab(itemResId);
        switch (itemResId){
            case R.id.menu_car:
                switchFragment(mCarFragment);
                break;
            case R.id.menu_mine:
                switchFragment(mMineFragment);
                break;
            default:
                break;
        }
    }

    private void switchFragment(Fragment fragment){
        if(fragment != mCurrentFragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(mCurrentFragment);
            if(!fragment.isAdded()){
                transaction.add(R.id.container_main,fragment);
            }
            transaction.show(fragment);
            transaction.commitAllowingStateLoss();
            mCurrentFragment = fragment;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //避免Fragment重叠
        super.onSaveInstanceState(new Bundle());
    }
}