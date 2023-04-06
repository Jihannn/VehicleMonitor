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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.BR;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.jihan.monitor.lib_common.base.BaseMvvmActivity;
import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.dapter.VehicleAdapter;
import com.jihan.monitor.phone.dapter.WarningMessageAdapter;
import com.jihan.monitor.phone.databinding.ActivityMessageBinding;
import com.jihan.monitor.phone.factory.AppInjection;
import com.jihan.monitor.phone.model.Vehicle;
import com.jihan.monitor.phone.model.WarningMessage;

import java.util.List;

public class WarningMessageActivity extends BaseMvvmActivity<WarningMessageViewModel, ActivityMessageBinding> {

    private static final String TAG = TAG_PHONE + WarningMessageActivity.class.getSimpleName();

    private WarningMessageAdapter mAdapter;

    public static class LAUNCHER{
        public static void launch(Context context){
            context.startActivity(new Intent(context,WarningMessageActivity.class));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mAdapter = new WarningMessageAdapter(R.layout.item_warning_message);
        mBinding.tbHome.setTitle(R.string.message);
        mBinding.tbHome.setTitleTextColor(Color.BLACK);
        mBinding.tbHome.setNavigationIcon(R.drawable.ic_back);
        mBinding.tbHome.setNavigationOnClickListener(v -> this.onBackPressed());
        setToolbarTitleCenter(mBinding.tbHome);
        RecyclerView recyclerView = mBinding.includeList.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(this::onLoadMore);
        mAdapter.addChildClickViewIds();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.includeList.recyclerView.setLayoutManager(layoutManager);
        mBinding.includeList.layoutRefresh.setOnRefreshListener(() -> {
            mBinding.includeList.layoutRefresh.setRefreshing(true);
            mAdapter.getLoadMoreModule().setEnableLoadMore(false);
            mViewModel.getWarningMessageList();
        });
        mBinding.includeList.recyclerView.setAdapter(mAdapter);
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
    protected void initObservable(WarningMessageViewModel viewModel) {
        mViewModel.WarningMessageLiveData.observe(this, this::handlerWarningMessageList);
    }

    @Override
    protected void loadData(WarningMessageViewModel viewModel) {
        mViewModel.getWarningMessageList();
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

    private void handlerWarningMessageList(BaseResponse<List<WarningMessage>> resp){
        mBinding.includeList.layoutRefresh.setRefreshing(false);
        mBinding.includeList.layoutRefresh.setEnabled(true);
        LogUtils.logI(TAG,"[handlerWarningMessageList]");
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setEnableLoadMore(true);
        if(resp == null){
            LogUtils.logI(TAG,"[handlerWarningMessageList]resp is null");
        }else{
            List<WarningMessage> warningMessageList = resp.getData();
            if(!warningMessageList.isEmpty()){
                mAdapter.setList(warningMessageList);
                loadMoreModule.loadMoreEnd();
            }
        }
        loadMoreModule.loadMoreEnd();
    }

    private void onLoadMore(){
        mBinding.includeList.layoutRefresh.setEnabled(false);
        mViewModel.getWarningMessageList();
    }
}
