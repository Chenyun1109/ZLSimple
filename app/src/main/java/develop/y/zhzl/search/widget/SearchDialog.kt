package develop.y.zhzl.search.widget

import android.content.Context
import android.view.View
import android.widget.EditText

import develop.y.zhzl.R
import framework.base.BaseDialog
import framework.utils.UIUtils

/**
 * by y on 2016/8/7.
 */
class SearchDialog private constructor(context: Context, searchInterface: SearchDialog.SearchInterface) : BaseDialog(context), View.OnClickListener {

    private var suffixEditText: EditText? = null
    private var limitEditText: EditText? = null
    private var searchInterface: SearchInterface? = null

    init {
        this.searchInterface = searchInterface
    }

    override fun onCreateView() {
        mView = View.inflate(context, R.layout.search_layout, null)
    }

    override fun initById() {
        mView!!.findViewById(R.id.btn_search).setOnClickListener(this)
        mView!!.findViewById(R.id.btn_annal).setOnClickListener(this)
        suffixEditText = mView!!.findViewById(R.id.suffix) as EditText
        limitEditText = mView!!.findViewById(R.id.limit) as EditText
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_search -> if (searchInterface != null) {
                searchInterface!!.startSearch(suffixEditText!!.text.toString().trim { it <= ' ' }, limitEditText!!.text.toString().trim { it <= ' ' })
                UIUtils.offKeyboard()
                dismiss()
            }
            R.id.btn_annal -> {
                AnnalActivity.startIntent()
                dismiss()
            }
        }
    }


    interface SearchInterface {
        fun startSearch(suffix: String, limit: String)
    }

    companion object {

        fun start(context: Context, searchInterface: SearchInterface) {
            SearchDialog(context, searchInterface)
        }
    }

}
