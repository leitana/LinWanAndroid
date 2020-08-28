package com.lx.linwanandroid.ui.activity

import android.annotation.SuppressLint
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.lx.linwanandroid.R
import com.lx.linwanandroid.base.BaseStatusViewActivity
import com.lx.linwanandroid.event.ColorEvent
import com.lx.linwanandroid.rxutils.RxSchedulerHelper
import com.lx.linwanandroid.ui.fragment.SettingFragment
import com.lx.linwanandroid.utils.SettingUtil
import io.reactivex.Observable
import io.reactivex.Observer
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * @title：SettingActivity
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/05
 */
class SettingActivity: BaseStatusViewActivity(), ColorChooserDialog.ColorCallback {

    private val EXTRA_SHOW_FRAGMENT = "show_fragment"
    private val EXTRA_SHOW_FRAGMENT_ARGUMENTS = "show_fragment_args"
    private val EXTRA_SHOW_FRAGMENT_TITLE = "show_fragment_title"

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun start() {

    }

    override fun initView() {

        val initFragment: String = intent.getStringExtra(EXTRA_SHOW_FRAGMENT) ?: ""
        val initArguments: Bundle = intent.getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS) ?: Bundle()
        val initTitle: String = intent.getStringExtra(EXTRA_SHOW_FRAGMENT_TITLE) ?:
            resources.getString(R.string.setting)

        if (initFragment.isEmpty()) {
            setUpFragment(SettingFragment::class.java.name, initArguments)
        } else{
            setUpFragment(initFragment, initArguments)
        }

        toolbar.run {
            title = initTitle
            setSupportActionBar(this)
            //返回按钮
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { finish() }
        }
    }

    override fun initData() {
    }

    @SuppressLint("WrongConstant")
    private fun setUpFragment(fragmentName: String, args: Bundle){
        val fragment: Fragment = Fragment.instantiate(this, fragmentName, args)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.replace(R.id.container, fragment)
//        transaction.commitAllowingStateLoss()
        transaction.commit()
    }

    private fun onBuildStartFragmentIntent(fragmentName: String, args: Bundle?, title: String?): Intent {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setClass(this, javaClass)
        intent.putExtra(EXTRA_SHOW_FRAGMENT, fragmentName)
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, args)
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, title)
        return intent
    }

    fun startWithFragment(fragmentName: String, args: Bundle?, resultTo: Fragment?, resultRequestCode: Int, title: String?) {
        val intent = onBuildStartFragmentIntent(fragmentName, args, title)
        if (resultTo == null) {
            startActivity(intent)
        } else {
            resultTo.startActivityForResult(intent, resultRequestCode)
        }
    }

    @SuppressLint("CheckResult")
    override fun onColorSelection(dialog: ColorChooserDialog, selectedColor: Int) {
        if (!dialog.isAccentMode) {
            SettingUtil.setColor(selectedColor)
        }
        initColor()
        Observable.timer(100, TimeUnit.MICROSECONDS)
            .compose(RxSchedulerHelper.io_main())
            .subscribe({
                EventBus.getDefault().post(ColorEvent(true))
            }, {})
    }

    override fun onColorChooserDismissed(dialog: ColorChooserDialog) {
    }
}