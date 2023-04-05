package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.jihan.monitor.lib_common.base.BaseMvvmFragment;
import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.BR;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.dapter.VehicleAdapter;
import com.jihan.monitor.phone.databinding.FragmentCarBinding;
import com.jihan.monitor.phone.factory.AppInjection;
import com.jihan.monitor.phone.model.Vehicle;

import java.util.List;

public class CarFragment extends BaseMvvmFragment<CarViewModel, FragmentCarBinding> {

    private static final String TAG = TAG_PHONE + CarFragment.class.getSimpleName();

    private VehicleAdapter mAdapter;

    @Override
    protected void initView() {
        mAdapter = new VehicleAdapter(R.layout.item_vehicle);
        mBinding.tbHome.setTitle(R.string.menu_car);
        mBinding.tbHome.setTitleTextColor(Color.BLACK);
        setToolbarTitleCenter(mBinding.tbHome);
        RecyclerView recyclerView = mBinding.includeList.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(this::onLoadMore);
        mAdapter.addChildClickViewIds();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.includeList.recyclerView.setLayoutManager(layoutManager);
        mBinding.includeList.layoutRefresh.setOnRefreshListener(() -> {
            mBinding.includeList.layoutRefresh.setRefreshing(true);
            mAdapter.getLoadMoreModule().setEnableLoadMore(false);
            mViewModel.getVehicleList();
        });
        mBinding.includeList.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car;
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
    protected void initObservable(CarViewModel viewModel) {
        mViewModel.vehicleListLiveData.observe(getViewLifecycleOwner(), this::handlerVehicleList);
    }

    @Override
    protected void loadData(CarViewModel viewModel) {
        mViewModel.getVehicleList();
    }

    private void handlerVehicleList(BaseResponse<List<Vehicle>> resp){
        if(resp == null){
            LogUtils.logI(TAG,"[handlerVehicleList]resp is null");
            return;
        }
        LogUtils.logI(TAG,"[handlerVehicleList]"+resp.getData().toString());
        List<Vehicle> vehicleList = resp.getData();
        if(vehicleList != null){
            mAdapter.setList(vehicleList);
            BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
            loadMoreModule.setEnableLoadMore(true);
            loadMoreModule.loadMoreEnd();
            mBinding.includeList.layoutRefresh.setRefreshing(false);
            mBinding.includeList.layoutRefresh.setEnabled(true);
        }
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

    private void onLoadMore(){
        mBinding.includeList.layoutRefresh.setEnabled(false);
        mViewModel.getVehicleList();
    }
}
