package com.lx.linwanandroid.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.lx.linwanandroid.R

/**
 * @title：DialogUtil
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/28
 */
object DialogUtil {
    fun getDialog(context: Context): AlertDialog.Builder{
        return AlertDialog.Builder(context)
    }

    fun getProgressDialog(context: Context, msg: String): ProgressDialog {
        val progressBar = ProgressDialog(context)
        if (msg.isNotBlank()) {
            progressBar.setMessage(msg)
        }
        return progressBar
    }

    fun getConfirmDialog(context: Context, message: String,
                         onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder{
        val builder = getDialog(context)
        builder.setMessage(message)
        builder.setPositiveButton("确定", onClickListener)
        builder.setNegativeButton("取消", null)
        return builder
    }
}