package com.jihan.lib_common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.jihan.lib_common.viewmodel.BaseViewModel2
import java.lang.reflect.ParameterizedType
//import com.jihan.lib_common.BR

abstract class BaseActivity2<VM : BaseViewModel2, DB : ViewDataBinding>(private val viewResId: Int) :
    AppCompatActivity() {

    lateinit var mBinding: DB
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initDataBinding()
//        initObserver()
        initView(savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        val type: Class<VM> =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this)[type]
        mViewModel.start()
    }

    private fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, viewResId)
//        mBinding.apply {
//            lifecycleOwner = this@BaseActivity
//            setVariable(BR.viewModel, mViewModel)
//        }
    }

//    open fun initObserver() {
//        mViewModel.errorResponse.observe(this) {
//            if (it.errorCode == Constant.ERROR_CODE_UN_LOGIN) {
//                Util.toast("尚未登录，无法进行操作")
//                ModuleMineApi.navToLoginActivity()
//            }
//        }
//    }

    abstract fun initView(savedInstanceState: Bundle?)
}