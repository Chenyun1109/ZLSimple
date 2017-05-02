package develop.y.zhzl.list.widget

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.xadapter.adapter.XBaseAdapter
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import develop.y.zhzl.R
import develop.y.zhzl.detail.widget.DetailActivity
import develop.y.zhzl.list.model.ListModel
import develop.y.zhzl.list.presenter.ListPresenterImpl
import develop.y.zhzl.list.view.IListView
import framework.base.BaseFragment
import framework.data.Constant
import framework.utils.ImageLoaderUtils
import framework.utils.UIUtils
import java.util.*


/**
 * by y on 2016/8/7.
 */
class ListFragment : BaseFragment<ListPresenterImpl>(),
        SwipeRefreshLayout.OnRefreshListener,
        IListView,
        XBaseAdapter.OnXBindListener<ListModel>,
        XBaseAdapter.OnItemClickListener<ListModel>,
        XBaseAdapter.OnItemLongClickListener<ListModel>,
        XBaseAdapter.OnXEmptyViewListener {

    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    private var mAdapter: XRecyclerViewAdapter<ListModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (null != bundle && !bundle.isEmpty) {
            pos = bundle.getInt(FRAGMENT_INDEX)
            type = bundle.getString(FRAGMENT_TYPE)
        }
    }

    override fun initPresenter(): ListPresenterImpl {
        return ListPresenterImpl(this)
    }

    override fun initById() {
        recyclerView = getView(R.id.recyclerView)
        swipeRefreshLayout = getView(R.id.srf_layout)
    }

    override fun initActivityCreated() {
        if (!lazyIsPrepared || !lazyIsVisible || lazyIsLoad) {
            return
        }
        mAdapter = XRecyclerViewAdapter<ListModel>()
        recyclerView!!.layoutManager = StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, LinearLayoutManager.VERTICAL)
        recyclerView!!.adapter = mAdapter!!.initXData(LinkedList<ListModel>())
                .setEmptyView(getView<View>(R.id.emptyView))
                .addRecyclerView(recyclerView)
                .setLayoutId(R.layout.list_item)
                .onXBind(this)
                .setOnItemClickListener(this)
                .setOnLongClickListener(this)
                .setOnXEmptyViewListener(this)

        swipeRefreshLayout!!.setOnRefreshListener(this)
        swipeRefreshLayout!!.post { this.onRefresh() }
        setLoad()
    }

    override val layoutId: Int
        get() = R.layout.fragment_list


    override fun onRefresh() {
        mPresenter.netWorkRequest(Constant.getSuffix(pos, type!!), Constant.LIMIT)
    }


    override fun removeAllAdapter() {
        mAdapter!!.removeAll()
    }

    override fun hideEmptyView() {
        mAdapter!!.hideEmptyView()
    }

    override fun isShowEmptyView() {
        mAdapter!!.isShowEmptyView()
    }


    override fun showProgress() {
        swipeRefreshLayout!!.isRefreshing = true
    }

    override fun hideProgress() {
        swipeRefreshLayout!!.isRefreshing = false
    }

    override fun onItemClick(view: View, position: Int, info: ListModel) {
        DetailActivity.startIntent(info.slug)
    }

    override fun onLongClick(view: View, position: Int, info: ListModel) {
        ListDialog.start(context, Constant.getSuffix(pos, type!!))
    }

    override fun onXBind(holder: XViewHolder, position: Int, listModel: ListModel) {
        holder.setTextView(R.id.list_tv, listModel.title)
        ImageLoaderUtils.display(holder.getImageView(R.id.list_image), listModel.titleImage!!)
    }

    override fun onXEmptyViewClick(view: View) {
        onRefresh()
    }

    override fun rxNetWorkError(e: Throwable) {
        isShowEmptyView()
        if (activity != null)
            UIUtils.snackBar(activity.findViewById(R.id.coordinatorLayout), getString(R.string.network_error))
    }

    override fun rxNetWorkSuccess(mData: List<ListModel>) {
        mAdapter!!.addAllData(mData)
    }

    companion object {

        fun newInstance(position: Int, type: String): ListFragment {
            val listFragment = ListFragment()
            val bundle = Bundle()
            bundle.putInt(listFragment.FRAGMENT_INDEX, position)
            bundle.putString(listFragment.FRAGMENT_TYPE, type)
            listFragment.arguments = bundle
            return listFragment
        }
    }

}
