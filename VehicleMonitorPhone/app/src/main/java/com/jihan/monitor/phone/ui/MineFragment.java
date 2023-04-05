package com.jihan.monitor.phone.ui;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.jihan.monitor.lib_common.base.BaseMvvmFragment;
import com.jihan.monitor.phone.BR;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.databinding.FragmentMineBinding;
import com.jihan.monitor.phone.factory.AppInjection;
import com.jihan.monitor.phone.model.UserManager;

public class MineFragment extends BaseMvvmFragment<MineViewModel, FragmentMineBinding> {
    @Override
    protected void initView() {
        mBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.logout();
                LoginActivity.LAUNCHER.launch(getContext());
                getActivity().finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
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
    protected void initObservable(MineViewModel viewModel) {

    }

    @Override
    protected void loadData(MineViewModel viewModel) {

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
}
