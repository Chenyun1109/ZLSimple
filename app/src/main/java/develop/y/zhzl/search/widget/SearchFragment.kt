package develop.y.zhzl.search.widget

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.xadapter.adapter.XBaseAdapter
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import develop.y.zhzl.R
import develop.y.zhzl.detail.widget.DetailActivity
import develop.y.zhzl.list.model.ListModel
import develop.y.zhzl.search.presenter.SearchPresenterImpl
import develop.y.zhzl.search.view.SearchView
import framework.base.BaseFragment
import framework.data.Constant
import framework.utils.ImageLoaderUtils
import framework.utils.UIUtils
import java.util.*

/**
 * by y on 2016/8/7.
 */
class SearchFragment : BaseFragment<SearchPresenterImpl>(),
        View.OnClickListener,
        SearchView,
        XBaseAdapter.OnItemClickListener<ListModel>,
        SearchDialog.SearchInterface,
        XBaseAdapter.OnXBindListener<ListModel> {

    private var floatingActionButton: FloatingActionButton? = null
    private var recyclerView: RecyclerView? = null
    private var textView: TextView? = null
    private var toolbar: Toolbar? = null
    private var progressBar: ProgressBar? = null

    private var mAdapter: XRecyclerViewAdapter<ListModel>? = null

    override fun initPresenter(): SearchPresenterImpl {
        return SearchPresenterImpl(this)
    }

    override fun initById() {
        floatingActionButton = activity.findViewById(R.id.fa_btn) as FloatingActionButton
        recyclerView = getView(R.id.recyclerView)
        textView = getView(R.id.search_explanation)
        progressBar = getView(R.id.progressBar)
        toolbar = activity.findViewById(R.id.toolbar) as Toolbar
    }

    override fun initActivityCreated() {
        showExplanation()
        floatingActionButton!!.setOnClickListener(this)
        mAdapter = XRecyclerViewAdapter<ListModel>()
        recyclerView!!.layoutManager = StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, LinearLayoutManager.VERTICAL)
        recyclerView!!.adapter = mAdapter!!
                .initXData(LinkedList<ListModel>())
                .addRecyclerView(recyclerView)
                .setLayoutId(R.layout.list_item)
                .setOnItemClickListener(this)
                .onXBind(this)
    }

    override val layoutId: Int
        get() = R.layout.fragment_search

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fa_btn -> SearchDialog.start(activity, this)
        }
    }


    override fun adapterRemove() {
        mAdapter!!.removeAll()
    }

    override fun suffixIsEmpty() {
        UIUtils.snackBar(floatingActionButton!!, getString(R.string.suffix_null))
    }

    override fun showExplanation() {
        textView!!.visibility = View.VISIBLE
    }

    override fun hideExplanation() {
        textView!!.visibility = View.GONE
    }


    override fun showProgress() {
        recyclerView!!.visibility = View.GONE
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        recyclerView!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.GONE
    }

    override fun onItemClick(view: View, position: Int, info: ListModel) {
        DetailActivity.startIntent(info.slug)
    }

    override fun startSearch(suffix: String, limit: String) {
        adapterRemove()
        mPresenter.netWorkRequest(suffix, limit)
    }

    override fun onXBind(holder: XViewHolder, position: Int, listModel: ListModel) {
        holder.setTextView(R.id.list_tv, listModel.title)
        ImageLoaderUtils.display(holder.getImageView(R.id.list_image), listModel.titleImage!!)
    }

    override fun rxNetWorkError(e: Throwable) {
        showExplanation()
        UIUtils.snackBar(floatingActionButton!!, getString(R.string.suffix_error))
    }

    override fun rxNetWorkSuccess(mData: List<ListModel>) {
        if (!mData.isEmpty()) {
            toolbar!!.title = mData[0].author!!.name
            mAdapter!!.addAllData(mData)
        }
    }

}
