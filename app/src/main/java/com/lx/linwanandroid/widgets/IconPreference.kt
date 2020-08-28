package com.lx.linwanandroid.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.afollestad.materialdialogs.color.CircleView
import com.lx.linwanandroid.R
import com.lx.linwanandroid.utils.SettingUtil

/**
 * @title：IconPreference
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/05
 */
class IconPreference(context: Context, attrs: AttributeSet): Preference(context, attrs) {

    private var circleImageView: CircleView? = null

    init {
        widgetLayoutResource = R.layout.item_icon_preference_preview
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val color = SettingUtil.getColor()
        circleImageView = holder?.itemView?.findViewById(R.id.iv_preview) as CircleView
        circleImageView!!.setBackgroundColor(color)
    }


    fun setView() {
        val color = SettingUtil.getColor()
        circleImageView!!.setBackgroundColor(color)
    }
}