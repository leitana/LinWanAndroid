package com.lx.linwanandroid.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lx.linwanandroid.bean.SearchHistoryBean

/**
 * @title：Dao
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/28
 */
@Dao
interface Dao {
    /**
     * 获取搜索历史
     */
    @get:Query("SELECT * FROM SearchHistoryBean")
    val searchHistoryList: MutableList<SearchHistoryBean>?

    /**
     * 插入搜索
     */
    @Insert
    fun insertSearchHistory(searchHistoryBean: SearchHistoryBean)

    /**
     * 根据id删除
     */
    @Query("DELETE FROM SearchHistoryBean WHERE id = :id")
    fun deleteBySheetId(id: Long)

    @Query("delete from SearchHistoryBean")
    fun deleteAllHistory()
}