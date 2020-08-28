package com.lx.linwanandroid.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @title：SearchHistoryBean
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/27
 */
@Entity
class SearchHistoryBean {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var key: String = ""
}