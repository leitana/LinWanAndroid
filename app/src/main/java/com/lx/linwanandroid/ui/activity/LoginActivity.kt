package com.lx.linwanandroid.ui.activity

import android.content.Intent
import com.lx.linwanandroid.R
import com.lx.linwanandroid.base.BaseMvpStatusViewActivity
import com.lx.linwanandroid.bean.Login
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.ext.showToast
import com.lx.linwanandroid.mvp.contract.LoginContract
import com.lx.linwanandroid.mvp.presenter.LoginPresenter
import com.lx.linwanandroid.utils.DialogUtil
import com.lx.linwanandroid.utils.Preference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @title：LoginActivity
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/28
 */
class LoginActivity: BaseMvpStatusViewActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {
    override fun createPresenter(): LoginContract.Presenter = LoginPresenter()

    private var user: String by Preference(Constant.USERNAME_KEY,"")

    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    private var token: String by Preference(Constant.TOKEN_KEY, "")

    private val mDialog by lazy {
        DialogUtil.getProgressDialog(this, getString(R.string.login_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        etUsername.setText(user)
        toolbar.run {
            title = resources.getString(R.string.login)
            setSupportActionBar(this)
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)//返回按钮
        }
        btLogin.setOnClickListener{
            login()
        }
        tvSignUp.setOnClickListener {

        }

    }

    override fun start() {
    }


    override fun loginSuccess(data: Login) {
        showToast(getString(R.string.login_success))
        isLogin = true
        user = data.username!!
        pwd = data.password!!
        token = data.token!!
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun loginFail() {
    }

    private fun login(){
        if (validate()) {
            mPresenter?.loginWanAndroid(etUsername.text.toString(), etPassword.text.toString())
        }
    }

    private fun validate(): Boolean{
        var valid = true
        val username: String = etUsername.text.toString()
        val password: String = etPassword.text.toString()

        if (username.isEmpty()) {
            etUsername.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            etPassword.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid
    }
}