package com.lx.linwanandroid.mvp.contract

import com.lx.base.IView

/**
 * @title：ContentContract
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/07/10
 */
interface ContentContract {

    interface View: CommonContract.View {

    }

    interface Presenter: CommonContract.Presenter<View> {

    }

    interface Model: CommonContract.Model {

    }
}