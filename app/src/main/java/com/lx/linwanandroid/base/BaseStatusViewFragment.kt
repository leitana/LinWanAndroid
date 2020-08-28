package com.lx.linwanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.lx.base.MultiStatusView
import com.lx.linwanandroid.app.App
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.utils.Preference

/**
 * @title：BaseStatusViewFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/08
 */
abstract class BaseStatusViewFragment : Fragment(){

    protected var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepared = false

    /**
     * 数据是否加载
     */
    private var hasLoadData = false

    /**
     * 多状态View
     */
    protected var mLayoutStatusView: MultiStatusView? = null

    /**
     * 加载视图
     */
    @LayoutRes
    abstract fun setContentView() : Int

    /**
     * 初始化View
     */
    abstract fun initView(view: View)

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(setContentView(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepared = true
        initView(view)
        lazyLoadPrepared()
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadPrepared()
        }
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    /**
     * 进行懒加载
     */
    private fun lazyLoadPrepared(){
        if (userVisibleHint && isViewPrepared && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.let {
            App.getRefWatcher(it)?.watch(this)
        }
    }
}