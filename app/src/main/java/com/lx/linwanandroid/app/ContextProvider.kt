package com.lx.linwanandroid.app

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

/**
 * @title：Preference
 * @projectName LinWanAndroid
 * @description: 使用 ContentProvider 无侵入获取 Context
 * @author linxiao
 * @data Created in 2020/09/01
 */
internal class ContextProvider : ContentProvider(){
    override fun onCreate(): Boolean {
        init(context!!)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return -1
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return -1
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    // 其他方法直接 return
}
// Context.kt
private lateinit var application : Context

fun init(context : Context){
    application= context
}

fun context() : Context{
    return application
}
