package com.lx.linwanandroid.event

import com.lx.linwanandroid.utils.SettingUtil

/**
 * @title：ColorEvent
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/05
 */
class ColorEvent(var isRefresh: Boolean, var color: Int = SettingUtil.getColor()) {
}