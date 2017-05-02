package develop.y.zhzl.search.widget

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.rxnetwork.bus.RxBus
import com.socks.library.KLog
import com.xadapter.adapter.XBaseAdapter
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import develop.y.zhzl.R
import develop.y.zhzl.main.widget.DarkViewActivity
import framework.data.Constant
import framework.sql.SearchSuffix
import framework.sql.sqlutil.GreenDaoDbUtils
import framework.utils.UIUtils
import java.util.*

/**
 * by y on 2016/8/17.
 */
class AnnalActivity : DarkViewActivity(),
        XBaseAdapter.OnXBindListener<SearchSuffix>,
        XBaseAdapter.OnItemClickListener<SearchSuffix> {

    private var recyclerView: RecyclerView? = null
    private var tvAnnal: TextView? = null
    private var toolbar: Toolbar? = null

    override fun initCreate() {
        toolbar!!.title = getString(R.string.annal_title)
        val mAdapter = XRecyclerViewAdapter<SearchSuffix>()

        if (GreenDaoDbUtils.suffixAll.isEmpty()) {
            tvAnnal!!.visibility = View.VISIBLE
        } else {
            val list = LinkedList<SearchSuffix>()
            list.addAll(GreenDaoDbUtils.suffixAll)
            recyclerView!!.layoutManager = StaggeredGridLayoutManager(Constant.RECYCLERVIEW_GRIDVIEW, StaggeredGridLayoutManager.VERTICAL)
            recyclerView!!.adapter = mAdapter
                    .initXData(list)
                    .addRecyclerView(recyclerView)
                    .setLayoutId(R.layout.annal_item)
                    .onXBind(this)
        }
    }

    override fun initById() {
        recyclerView = getView(R.id.recyclerView)
        tvAnnal = getView(R.id.is_tv_annal)
        toolbar = getView(R.id.toolbar)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_annal
    }

    override fun isSwipeBackLayout(): Boolean {
        return true
    }

    override fun onItemClick(view: View, position: Int, info: SearchSuffix) {}

    override fun onXBind(holder: XViewHolder, position: Int, searchSuffix: SearchSuffix) {
        KLog.i(searchSuffix.suffix)
        holder.setTextView(R.id.tv_annal, searchSuffix.suffix)
        holder.getTextView(R.id.tv_annal).setOnClickListener {
            RxBus.getInstance().send(Constant.ANNAL_TAG, holder.getTextView(R.id.tv_annal).text.toString())
            finish()
        }
    }

    companion object {

        fun startIntent() {
            UIUtils.startActivity(AnnalActivity::class.java)
        }
    }
}
