package com.jihan.lib_common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.jihan.lib_common.viewmodel.BaseViewModel2
import java.lang.reflect.ParameterizedType
//import com.jihan.lib_common.BR

abstract class BaseFragment2<VM : BaseViewModel2, DB : ViewDataBinding>(private val viewResId: Int) : Fragment() {

    lateinit var mBinding:DB
    lateinit var mViewModel: VM
    private var mIsFirstLoading = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater,viewResId,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsFirstLoading = true
        initViewModel()
        initBinding()
//        initObserver()
        initView()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        val type: Class<VM> =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this)[type]
        mViewModel.start()
    }

    private fun initBinding() {
//        mBinding.apply {
//            lifecycleOwner = viewLifecycleOwner
//            setVariable(BR.viewModel, mViewModel)
//        }
    }

//    open fun initObserver() {
//        mViewModel.errorResponse.observe(viewLifecycleOwner) {
//            if (it.errorCode == Constant.ERROR_CODE_UN_LOGIN) {
//                Util.toast("尚未登录，无法进行操作")
//                ModuleMineApi.navToLoginActivity()
//            }
//        }
//    }

    abstract fun initView()

    override fun onResume() {
        super.onResume()
        if (lifecycle.currentState == Lifecycle.State.STARTED && mIsFirstLoading) {
            lazyLoadData()
            mIsFirstLoading = false
        }
    }

    open fun lazyLoadData() {}
}