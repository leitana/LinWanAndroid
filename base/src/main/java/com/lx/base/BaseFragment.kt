package com.lx.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * @title：BaseFragment
 * @projectName RouteModule
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/05/15
 */
abstract class BaseFragment : Fragment(){
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepared = false

    /**
     * 数据是否加载
     */
    private var hasLoadData = false

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
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadPrepared()
        }
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
}