package com.lx.linwanandroid.bean

import java.io.Serializable

/**
 * @title：ProjectBean
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
class ProjectTreeBean: Serializable {
    var children: List<Any>? = null
    var courseId: Int = 0
    var id: Int = 0
    var name: String? = null
    var order: Int = 0
    var parentChapterId: Int = 0
    var userControlSetTop = false
    var visible = 0
}