package develop.y.zhzl.main.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView

import com.rxnetwork.bus.RxBus

import develop.y.zhzl.R
import framework.App
import framework.data.Constant
import framework.utils.CacheUtils
import framework.utils.UIUtils

class TransitionActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)
        val imageView = findViewById(R.id.imageview) as ImageView
        val bitmap = CacheUtils.instance.get<Bitmap>(Constant.BITMAP_CACHE_KEY)
        imageView.setImageBitmap(bitmap)
        startAnimation()
    }


    private fun startAnimation() {
        val animator = ValueAnimator.ofFloat(1f).setDuration(400)
        animator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                App.instance.refreshAllActivity()
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                RxBus.getInstance().send(Constant.THEME_TAG)
                finish()
                overridePendingTransition(0, android.R.anim.fade_out)
            }
        })
        animator.start()
    }

    companion object {

        fun startIntent() {
            UIUtils.startActivity(TransitionActivity::class.java)
        }
    }
}
