package framework.utils


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import develop.y.zhzl.R
import framework.App


/**
 * by y on 2016/8/7.
 */
object UIUtils {


    val context: Context
        get() = App.instance

    fun getDrawable(id: Int): Drawable {
        return ContextCompat.getDrawable(context, id)
    }

    fun getColor(id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    fun getString(id: Int): String {
        return context.resources.getString(id)
    }

    fun getStringArray(id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }

    fun startActivity(clz: Class<*>) {
        val intent = Intent(context, clz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun startActivity(clz: Class<*>, bundle: Bundle) {
        val intent = Intent(context, clz)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun offKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun toast(`object`: Any) {
        Toast.makeText(context, `object`.toString() + "", Toast.LENGTH_LONG).show()
    }

    fun snackBar(view: View, `object`: Any) {
        Snackbar.make(view, `object`.toString() + "", Snackbar.LENGTH_SHORT).show()
    }

    fun snackBar(view: View, `object`: Any, color: Int) {
        Snackbar.make(view, `object`.toString() + "", Snackbar.LENGTH_SHORT)
                .setActionTextColor(color)
                .show()
    }

    fun share(activity: Activity, message: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(Intent.createChooser(intent, getString(R.string.share)))
    }

    fun captureContent(activity: Activity): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val b1 = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val width = activity.windowManager.defaultDisplay.width
        val height = activity.windowManager.defaultDisplay.height
        val b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return b
    }


}
