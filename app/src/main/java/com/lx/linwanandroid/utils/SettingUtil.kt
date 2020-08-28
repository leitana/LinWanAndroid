package com.lx.linwanandroid.utils

import android.graphics.Color
import android.preference.PreferenceManager
import com.lx.linwanandroid.R
import com.lx.linwanandroid.app.App

/**
 * @title：ThemeColorUtils
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/02
 */
object SettingUtil {
    private val setting = PreferenceManager.getDefaultSharedPreferences(App.context)

    fun getIsNoPhotoMode(): Boolean{
        return setting.getBoolean("switch_noPhotoMode", false)
    }

    fun getIsShowTopArticle(): Boolean{
        return setting.getBoolean("switch_show_top", false)
    }

    /**
     * 获取主题颜色
     */
    fun getColor(): Int{
        val defaultColor = App.context.resources.getColor(R.color.colorPrimary)
        val color = setting.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else color
    }

    /**
     * 设置主题色
     */
    fun setColor(color: Int){
        setting.edit().putInt("color", color).apply()
    }

    /**
     * 导航栏是否上色
     */
    fun getNavBar(): Boolean {
        return setting.getBoolean("nav_bar", false)
    }

    /**
     * 设置是否夜间模式
     */
    fun setIsNightMode(flag: Boolean){
        setting.edit().putBoolean("switch_nightMode", flag).apply()
    }

    /**
     * 是否夜间模式
     */
    fun isNightMode(): Boolean{
        return setting.getBoolean("switch_nightMode", false)
    }

    /**
     * 是否开启自动夜间模式
     */
    fun isAutoNightMode(): Boolean{
        return setting.getBoolean("auto_nightMode", false)
    }

    fun getNightStartHour(): String {
        return setting.getString("night_startHour","22")!!
    }

    fun setNightStartHour(nightStartHour: String) {
        setting.edit().putString("night_startHour", nightStartHour).apply()
    }

    fun getNightStartMinute(): String {
        return setting.getString("night_startMinute", "00")!!
    }

    fun setNightStartMinute(nightStartMinute: String) {
        setting.edit().putString("night_startMinute", nightStartMinute).apply()
    }

    fun getDayStartHour(): String {
        return setting.getString("day_startHour", "06")!!
    }

    fun setDayStartHour(dayStartHour: String) {
        setting.edit().putString("day_startHour", dayStartHour).apply()
    }

    fun getDayStartMinute(): String {
        return setting.getString("day_startMinute", "00")!!
    }

    fun setDayStartMinute(dayStartMinute: String) {
        setting.edit().putString("day_startMinute", dayStartMinute).apply()
    }

}