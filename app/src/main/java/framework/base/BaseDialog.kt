package framework.base

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View

/**
 * by y on 2016/10/27
 */

abstract class BaseDialog protected constructor(context: Context) : AlertDialog(context) {

    var mView: View? = null

    init {
        onCreateView()
        initById()
        if (mView != null) {
            setView(mView)
            show()
        }
    }

    protected abstract fun onCreateView()

    protected abstract fun initById()

}
