package com.lx.linwanandroid.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import com.afollestad.materialdialogs.color.CircleView
import com.lx.base.BaseActivity
import com.lx.base.MultiStatusView
import com.lx.linwanandroid.R
import com.lx.linwanandroid.app.App
import com.lx.linwanandroid.constant.Constant.LOGIN_KEY
import com.lx.linwanandroid.utils.Preference
import com.lx.linwanandroid.utils.StatusBarUtil
import com.lx.linwanandroid.utils.SettingUtil

/**
 * @title：BaseStatusViewActivity
 * @projectName LinWanAndroid
 * @description: 融合了多状态View的基类
 * @author linxiao
 * @data Created in 2020/06/02
 */
abstract class BaseStatusViewActivity : BaseActivity(){
    //check login
    protected var isLogin: Boolean by Preference(LOGIN_KEY, false)

    //主题色
    protected var mThemeColor: Int = SettingUtil.getColor()

    //多状态View
    protected var mLayoutStatusView: MultiStatusView ?= null


    /**
     * 开始请求
     */
    abstract fun start()

    override fun actionAfterView() {
        start()
    }

    override fun onResume() {
        super.onResume()
        initColor()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.getRefWatcher(this)?.watch(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Fragment 逐个出栈
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }



    private fun initListener() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    open fun initColor(){
        mThemeColor = if (!SettingUtil.isNightMode()) {
            SettingUtil.getColor()
        } else {
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarUtil.setColor(this, mThemeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (SettingUtil.getNavBar()) {
                window.navigationBarColor = CircleView.shiftColorDown(mThemeColor)
            } else {
                window.navigationBarColor = Color.BLACK
            }
        }

    }
}