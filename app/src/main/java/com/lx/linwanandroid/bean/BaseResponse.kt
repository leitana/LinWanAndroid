package com.lx.linwanandroid.bean

import com.google.gson.annotations.SerializedName

/**
 * BaseResponse<T>
</T> */
data class BaseResponse<T>(val data: T) : BaseBean(){


}