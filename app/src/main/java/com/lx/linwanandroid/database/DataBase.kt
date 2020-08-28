package com.lx.linwanandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lx.linwanandroid.bean.SearchHistoryBean

/**
 * @title：DataBase
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/28
 */
@Database(entities = [SearchHistoryBean::class], version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun dao(): Dao
}