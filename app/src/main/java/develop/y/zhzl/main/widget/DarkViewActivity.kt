package develop.y.zhzl.main.widget

import android.content.Context
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.View
import android.view.WindowManager

import develop.y.zhzl.R
import framework.base.BaseActivity

abstract class DarkViewActivity : BaseActivity() {

    private var mWindowManager: WindowManager? = null
    private var darkView: View? = null
    private var isAddedView: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAddedView = false
        if (!themeType) {
            initNightView()
            darkView!!.setBackgroundResource(R.color.night_mask)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isAddedView) {
            mWindowManager!!.removeViewImmediate(darkView)
            mWindowManager = null
            darkView = null
        }
    }

    protected fun initNightView() {
        if (isAddedView) {
            return
        }
        val layoutParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT)
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        darkView = View(this)
        mWindowManager!!.addView(darkView, layoutParams)
        isAddedView = true
    }
}
