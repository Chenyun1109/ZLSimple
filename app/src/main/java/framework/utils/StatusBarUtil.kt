package framework.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout

object StatusBarUtil {

    private val DEFAULT_STATUS_BAR_ALPHA = 112

    /**
     * 设置状态栏颜色

     * @param activity       需要设置的activity
     * *
     * @param color          状态栏颜色值
     * *
     * @param statusBarAlpha 状态栏透明度
     */
    @JvmOverloads fun setColor(activity: Activity, color: Int, statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = calculateStatusColor(color, statusBarAlpha)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val decorView = activity.window.decorView as ViewGroup
            if (decorView.childCount > 0 && decorView.getChildAt(0) is StatusBarView) {
                decorView.getChildAt(0).setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
            } else {
                val statusView = createStatusBarView(activity, color, statusBarAlpha)
                decorView.addView(statusView, 0)
            }
            setRootView(activity)
        }
    }

    /**
     * 设置状态栏纯色 不加半透明效果

     * @param activity 需要设置的 activity
     * *
     * @param color    状态栏颜色值
     */
    fun setColorNoTranslucent(activity: Activity, color: Int) {
        setColor(activity, color, 0)
    }

    /**
     * 设置状态栏颜色(5.0以下无半透明效果,不建议使用)

     * @param activity 需要设置的 activity
     * *
     * @param color    状态栏颜色值
     */
    fun setColorDiff(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // 生成一个状态栏大小的矩形
        val decorView = activity.window.decorView as ViewGroup
        if (decorView.childCount > 0 && decorView.getChildAt(0) is StatusBarView) {
            decorView.getChildAt(0).setBackgroundColor(color)
        } else {
            val statusView = createStatusBarView(activity, color)
            decorView.addView(statusView, 0)
        }
        setRootView(activity)
    }

    /**
     * 使状态栏半透明
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏

     * @param activity       需要设置的activity
     * *
     * @param statusBarAlpha 状态栏透明度
     */
    @JvmOverloads fun setTranslucent(activity: Activity, statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparent(activity)
        addTranslucentView(activity, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏全透明

     * @param activity 需要设置的activity
     */
    fun setTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        setRootView(activity)
    }

    /**
     * 使状态栏透明(5.0以上半透明效果,不建议使用)
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏

     * @param activity 需要设置的activity
     */
    fun setTranslucentDiff(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setRootView(activity)
        }
    }

    /**
     * 为DrawerLayout 布局设置状态栏颜色,纯色

     * @param activity     需要设置的activity
     * *
     * @param drawerLayout DrawerLayout
     * *
     * @param color        状态栏颜色值
     */
    fun setColorNoTranslucentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout, color: Int) {
        setColorForDrawerLayout(activity, drawerLayout, color, 0)
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色

     * @param activity       需要设置的activity
     * *
     * @param drawerLayout   DrawerLayout
     * *
     * @param color          状态栏颜色值
     * *
     * @param statusBarAlpha 状态栏透明度
     */
    @JvmOverloads fun setColorForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout, color: Int, statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        // 生成一个状态栏大小的矩形
        // 添加 statusBarView 到布局中
        val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
        if (contentLayout.childCount > 0 && contentLayout.getChildAt(0) is StatusBarView) {
            contentLayout.getChildAt(0).setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
        } else {
            val statusBarView = createStatusBarView(activity, color)
            contentLayout.addView(statusBarView, 0)
        }
        // 内容布局不是 LinearLayout 时,设置padding top
        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
        }
        // 设置属性
        val drawer = drawerLayout.getChildAt(1) as ViewGroup
        drawerLayout.fitsSystemWindows = false
        contentLayout.fitsSystemWindows = false
        contentLayout.clipToPadding = true
        drawer.fitsSystemWindows = false

        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色(5.0以下无半透明效果,不建议使用)

     * @param activity     需要设置的activity
     * *
     * @param drawerLayout DrawerLayout
     * *
     * @param color        状态栏颜色值
     */
    fun setColorForDrawerLayoutDiff(activity: Activity, drawerLayout: DrawerLayout, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 生成一个状态栏大小的矩形
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            if (contentLayout.childCount > 0 && contentLayout.getChildAt(0) is StatusBarView) {
                contentLayout.getChildAt(0).setBackgroundColor(calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA))
            } else {
                // 添加 statusBarView 到布局中
                val statusBarView = createStatusBarView(activity, color)
                contentLayout.addView(statusBarView, 0)
            }
            // 内容布局不是 LinearLayout 时,设置padding top
            if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
            }
            // 设置属性
            val drawer = drawerLayout.getChildAt(1) as ViewGroup
            drawerLayout.fitsSystemWindows = false
            contentLayout.fitsSystemWindows = false
            contentLayout.clipToPadding = true
            drawer.fitsSystemWindows = false
        }
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明

     * @param activity     需要设置的activity
     * *
     * @param drawerLayout DrawerLayout
     */
    @JvmOverloads fun setTranslucentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout, statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparentForDrawerLayout(activity, drawerLayout)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明

     * @param activity     需要设置的activity
     * *
     * @param drawerLayout DrawerLayout
     */
    fun setTransparentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
        // 内容布局不是 LinearLayout 时,设置padding top
        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
        }

        // 设置属性
        val drawer = drawerLayout.getChildAt(1) as ViewGroup
        drawerLayout.fitsSystemWindows = false
        contentLayout.fitsSystemWindows = false
        contentLayout.clipToPadding = true
        drawer.fitsSystemWindows = false
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明(5.0以上半透明效果,不建议使用)

     * @param activity     需要设置的activity
     * *
     * @param drawerLayout DrawerLayout
     */
    fun setTranslucentForDrawerLayoutDiff(activity: Activity, drawerLayout: DrawerLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 设置内容布局属性
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            contentLayout.fitsSystemWindows = true
            contentLayout.clipToPadding = true
            // 设置抽屉布局属性
            val vg = drawerLayout.getChildAt(1) as ViewGroup
            vg.fitsSystemWindows = false
            // 设置 DrawerLayout 属性
            drawerLayout.fitsSystemWindows = false
        }
    }

    /**
     * 添加半透明矩形条

     * @param activity       需要设置的 activity
     * *
     * @param statusBarAlpha 透明值
     */
    fun addTranslucentView(activity: Activity, statusBarAlpha: Int) {
        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        // 移除半透明矩形,以免叠加
        if (contentView.childCount > 1) {
            contentView.getChildAt(1).setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0))
        } else {
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha))
        }
    }

    /**
     * 生成一个和状态栏大小相同的彩色矩形条

     * @param activity 需要设置的 activity
     * *
     * @param color    状态栏颜色值
     * *
     * @return 状态栏矩形条
     */
    fun createStatusBarView(activity: Activity, color: Int): StatusBarView {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = StatusBarView(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(color)
        return statusBarView
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条

     * @param activity 需要设置的activity
     * *
     * @param color    状态栏颜色值
     * *
     * @param alpha    透明值
     * *
     * @return 状态栏矩形条
     */
    fun createStatusBarView(activity: Activity, color: Int, alpha: Int): StatusBarView {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = StatusBarView(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
        return statusBarView
    }

    /**
     * 设置根布局参数
     */
    fun setRootView(activity: Activity) {
        val rootView = (activity.findViewById(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        rootView.fitsSystemWindows = true
        rootView.clipToPadding = true
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun transparentStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 创建半透明矩形 View

     * @param alpha 透明值
     * *
     * @return 半透明 View
     */
    fun createTranslucentStatusBarView(activity: Activity, alpha: Int): StatusBarView {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = StatusBarView(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
        return statusBarView
    }

    /**
     * 获取状态栏高度

     * @param context context
     * *
     * @return 状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 计算状态栏颜色

     * @param color color值
     * *
     * @param alpha alpha值
     * *
     * @return 最终的状态栏颜色
     */
    fun calculateStatusColor(color: Int, alpha: Int): Int {
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }

    /**
     * 为有 ImageView 的Activity 添加半透明状态栏

     * @param act        需要设置的Activity
     * *
     * @param marginView 需要设置 margin 的 View
     */
    fun setTranslucentForImageView(act: Activity, marginView: View) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            act.window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val vmlp = marginView.layoutParams as ViewGroup.MarginLayoutParams
            vmlp.setMargins(0, -getStatusBarHeight(act), 0, 0)
        }
    }

    /**
     * 改变小米的状态栏字体颜色为黑色, 要求 MIUI6 以上
     */
    @Throws(Exception::class)
    fun processMIUI(activity: Activity, lightStatusBar: Boolean) {
        val clazz = activity.window.javaClass
        val darkModeFlag: Int
        val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        darkModeFlag = field.getInt(layoutParams)
        val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
        extraFlagField.invoke(activity.actionBar, if (lightStatusBar) darkModeFlag else 0, darkModeFlag)
    }

    /**
     * 改变魅族的状态栏字体为黑色，要求 FlyMe4 以上
     */
    @Throws(Exception::class)
    fun processFlyMe(activity: Activity, isLightStatusBar: Boolean) {
        val lp = activity.window.attributes
        val instance = Class.forName("android.view.WindowManager\$LayoutParams")
        val value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp)
        val field = instance.getDeclaredField("meizuFlags")
        field.isAccessible = true
        val origin = field.getInt(lp)
        if (isLightStatusBar) {
            field.set(lp, origin or value)
        } else {
            field.set(lp, value.inv() and origin)
        }
    }

    class StatusBarView : View {
        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

        constructor(context: Context) : super(context) {}
    }
}