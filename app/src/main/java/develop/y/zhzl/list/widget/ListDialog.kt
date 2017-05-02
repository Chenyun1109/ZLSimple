package develop.y.zhzl.list.widget

import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView

import develop.y.zhzl.R
import framework.base.BaseDialog
import framework.utils.UIUtils


/**
 * by y on 2016/8/7.
 */
class ListDialog private constructor(context: Context) : BaseDialog(context), View.OnClickListener {

    private var textView: TextView? = null
    private var suffix: String? = null

    override fun onCreateView() {
        mView = View.inflate(context, R.layout.list_dialog_layout, null)
    }

    override fun initById() {
        textView = mView!!.findViewById(R.id.tv_title) as TextView
        mView!!.findViewById(R.id.btn_list).setOnClickListener(this)
    }

    private fun initData(suffix: String) {
        this.suffix = suffix
        textView!!.text = TextUtils.concat(UIUtils.getString(R.string.suffix_title), this.suffix, "")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_list -> {
                val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.text = suffix
                UIUtils.toast(UIUtils.getString(R.string.suffix_copy))
                dismiss()
            }
        }
    }

    companion object {

        fun start(context: Context, suffix: String) {
            ListDialog(context).initData(suffix)
        }
    }
}
