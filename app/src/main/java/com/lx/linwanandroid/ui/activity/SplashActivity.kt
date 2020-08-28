package com.lx.linwanandroid.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.SystemClock
import com.lx.base.BaseActivity
import com.lx.linwanandroid.R
import com.lx.linwanandroid.constant.Constant
import com.lx.linwanandroid.utils.Preference
import com.lx.linwanandroid.utils.StatusBarUtil
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * @title：SplashActivity
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/17
 */
class SplashActivity: BaseActivity() {

    var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    private val ANIMATION_TIME = 2000

    private val SCALE_END = 1.13f

    private val IMAGES = intArrayOf(
        R.drawable.ic_screen_default,
        R.drawable.splash0,
        R.drawable.splash1,
        R.drawable.splash2,
        R.drawable.splash3,
        R.drawable.splash4,
        R.drawable.splash5,
        R.drawable.splash6,
        R.drawable.splash7,
        R.drawable.splash8,
        R.drawable.splash9,
        R.drawable.splash10,
        R.drawable.splash11,
        R.drawable.splash12,
        R.drawable.splash13,
        R.drawable.splash14,
        R.drawable.splash15,
        R.drawable.splash16
    )
    override fun getLayoutId(): Int = R.layout.activity_splash


    @SuppressLint("CheckResult")
    override fun initView() {
        StatusBarUtil.transparentStatusBar(this)
        val random = Random(SystemClock.elapsedRealtime())
        image.setImageResource(IMAGES[random.nextInt(IMAGES.size)])

        Observable.timer(1000, TimeUnit.MICROSECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { startAnim() }
    }

    override fun initData() {
    }


    override fun actionAfterView() {
    }

    fun startAnim() {
        val animatorX = ObjectAnimator.ofFloat(image, "scaleX", 1f, SCALE_END)
        val animatorY = ObjectAnimator.ofFloat(image, "scaleY", 1f, SCALE_END)

        val set = AnimatorSet()
        set.setDuration(ANIMATION_TIME.toLong()).play(animatorX)
            .with(animatorY)
        set.start()

        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (isLogin) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else{
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        })
    }
}