package com.lx.linwanandroid.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceFragment
import androidx.appcompat.app.AlertDialog
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.lx.linwanandroid.R
import com.lx.linwanandroid.event.RefreshHomeEvent
import com.lx.linwanandroid.ext.showSnackMsg
import com.lx.linwanandroid.rxutils.RxSchedulerHelper
import com.lx.linwanandroid.ui.activity.SettingActivity
import com.lx.linwanandroid.utils.CacheDataUtil
import com.lx.linwanandroid.widgets.IconPreference
import io.reactivex.Observable
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * @title：SettingFragment
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/05
 */
class SettingFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var context: SettingActivity? = null
    private var colorPreview: IconPreference? = null

    companion object {
        fun getInstance(): SettingFragment{
            return SettingFragment()
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_setting, rootKey)
        context = activity as SettingActivity

        colorPreview = findPreference("color")

        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        setCacheText()

        /**
         * 是否显示首页置顶文章
         */
        findPreference<CheckBoxPreference>("switch_show_top")?.setOnPreferenceChangeListener { preference, newValue ->
            // 通知首页刷新数据
            // 延迟发送通知：为了保证刷新数据时 SettingUtil.getIsShowTopArticle() 得到最新的值
            Observable.timer(100, TimeUnit.MICROSECONDS)
                .compose(RxSchedulerHelper.io_main())
                .subscribe({
                    EventBus.getDefault().post(RefreshHomeEvent(true))
                },{})
            true
        }

        /**
         * 自动夜间模式
         */
        findPreference<Preference>("auto_nightMode")?.setOnPreferenceClickListener {
            context?.startWithFragment(AutoNightModeFragment::class.java.name, null, null, 0, null)
            true
        }

        /**
         * 换主题颜色
         */
        findPreference<IconPreference>("color")?.setOnPreferenceClickListener {
            ColorChooserDialog.Builder(context!!, R.string.choose_theme_color)
                .backButton(R.string.back)
                .cancelButton(R.string.cancel)
                .doneButton(R.string.done)
                .customButton(R.string.custom)
                .presetsButton(R.string.back)
                .allowUserColorInputAlpha(false)
                .show()
            true
        }

        /**
         * 清除缓存
         */
        findPreference<Preference>("clearCache")?.setOnPreferenceClickListener {
            CacheDataUtil.clearAllCache(context!!)
            context?.showSnackMsg(getString(R.string.clear_cache_successfully))
            setCacheText()
            true
        }

        /**
         * 版本名
         */
        val version = context?.resources?.getString(R.string.current_version).toString()
            .plus(context?.packageManager?.getPackageInfo(context?.packageName, 0)?.versionName)
        findPreference<Preference>("version")?.summary = version

        findPreference<Preference>("official_website")?.setOnPreferenceClickListener {
            context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.official_website_url))))
            false
        }

        findPreference<Preference>("copyRight")?.setOnPreferenceClickListener {
            AlertDialog.Builder(context!!)
                .setTitle(R.string.copyright)
                .setMessage(R.string.copyright_content)
                .setCancelable(true)
                .show()
            false
        }
    }



    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        key ?: return
        if (key == "color") {
            colorPreview?.setView()
        }
    }

    /**
     * 设置缓存大小
     */
    private fun setCacheText() {
        findPreference<Preference>("clearCache")?.summary = CacheDataUtil.getTotalCacheSize(context!!)
    }
}