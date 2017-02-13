package develop.y.zhzl.list.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.LinkedList;
import java.util.List;

import develop.y.zhzl.R;
import develop.y.zhzl.detail.widget.DetailActivity;
import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.list.presenter.ListPresenter;
import develop.y.zhzl.list.presenter.ListPresenterImpl;
import develop.y.zhzl.list.view.IListView;
import framework.base.BaseFragment;
import framework.data.Constant;
import framework.utils.ImageLoaderUtils;
import framework.utils.UIUtils;
import rx.Observable;

import static framework.data.Constant.getSuffix;

/**
 * by y on 2016/8/7.
 */
public class ListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, IListView,
        XBaseAdapter.OnXBindListener<ListModel>,
        XBaseAdapter.OnItemClickListener<ListModel>,
        XBaseAdapter.OnItemLongClickListener<ListModel>,
        XBaseAdapter.OnXEmptyViewListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ListPresenter listPresenter;
    private XRecyclerViewAdapter<ListModel> mAdapter;


    public static ListFragment newInstance(int position, String type) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        bundle.putString(FRAGMENT_TYPE, type);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle && !bundle.isEmpty()) {
            pos = bundle.getInt(FRAGMENT_INDEX);
            type = bundle.getString(FRAGMENT_TYPE);
        }
    }


    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.srf_layout);
    }

    @Override
    protected void initData() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        listPresenter = new ListPresenterImpl(this);
        mAdapter = new XRecyclerViewAdapter<>();

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(
                mAdapter.initXData(new LinkedList<>())
                        .setEmptyView(getView(R.id.emptyView))
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.list_item)
                        .onXBind(this)
                        .setOnItemClickListener(this)
                        .setOnLongClickListener(this)
                        .setOnXEmptyViewListener(this)
        );

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }


    @Override
    public void onRefresh() {
        listPresenter.netWorkRequest(getSuffix(pos, type), Constant.LIMIT);
    }


    @Override
    public void setData(List<ListModel> data) {
        mAdapter.addAllData(data);
    }

    @Override
    public void removeAllAdapter() {
        mAdapter.removeAll();
    }

    @Override
    public void hideEmptyView() {
        mAdapter.hideEmptyView();
    }

    @Override
    public void isShowEmptyView() {
        mAdapter.isShowEmptyView();
    }

    @Override
    public void viewBindToLifecycle(Observable<List<ListModel>> observable) {
        if (observable != null) {
            observable.compose(this.bindToLifecycle());
        }
    }


    @Override
    public void netWorkError() {
        isShowEmptyView();
        if (getActivity() != null)
            UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position, ListModel info) {
        DetailActivity.startIntent(info.getSlug());
    }

    @Override
    public void onLongClick(View view, int position, ListModel info) {
        ListDialog.start(getContext(), getSuffix(pos, type));
    }

    @Override
    public void onXBind(XViewHolder holder, int position, ListModel listModel) {
        holder.setTextView(R.id.list_tv, listModel.getTitle());
        ImageLoaderUtils.display(holder.getImageView(R.id.list_image), listModel.getTitleImage());
    }

    @Override
    public void onXEmptyViewClick(View view) {
        onRefresh();
    }
}
