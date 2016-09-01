package develop.y.zhzl.list.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

import develop.y.zhzl.R;
import develop.y.zhzl.detail.widget.DetailActivity;
import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.list.presenter.ListPresenter;
import develop.y.zhzl.list.presenter.ListPresenterImpl;
import develop.y.zhzl.list.view.IListView;
import framework.base.BaseFragment;
import framework.base.BaseRecyclerViewAdapter;
import framework.data.Constant;
import framework.utils.ImageLoaderUtils;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class ListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, IListView,
        BaseRecyclerViewAdapter.OnItemClickListener<ListModel>, BaseRecyclerViewAdapter.OnItemLongClickListener<ListModel> {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ListPresenter listPresenter;
    private ListAdapter adapter;


    public static ListFragment newInstance(int position, String type) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = UIUtils.getBundle();
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

        List<ListModel> list = new LinkedList<>();
        adapter = new ListAdapter(list);
        adapter.setOnItemClickListener(this);
        adapter.setOnLongClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }


    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onRefresh() {
        adapter.removeAll();
        listPresenter.netWorkRequest(getSuffix(pos, type), Constant.LIMIT);
    }


    @Override
    public void setData(List<ListModel> data) {
        if (!data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void netWorkError() {
        UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), UIUtils.getString(R.string.network_error));
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
        ListDialog.copySuffix(getActivity(), getSuffix(pos, type));
    }

    private String getSuffix(int position, String type) {
        switch (type) {
            case Constant.ZHIHU:
                return UIUtils.getStringArray(R.array.zhihu_suffix)[position];

            case Constant.MOVIE:
                return UIUtils.getStringArray(R.array.movie_suffix)[position];

            case Constant.MUSIC:
                return UIUtils.getStringArray(R.array.music_suffix)[position];

            case Constant.DEVELOP:
                return UIUtils.getStringArray(R.array.develop_suffix)[position];

            case Constant.BOOK:
                return UIUtils.getStringArray(R.array.book_suffix)[position];

            case Constant.INTERNET:
                return UIUtils.getStringArray(R.array.internet_suffix)[position];
            default:
                return "";
        }
    }

    public class ListAdapter extends BaseRecyclerViewAdapter<ListModel> {

        public ListAdapter(List<ListModel> mDatas) {
            super(mDatas);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.list_item;
        }

        @Override
        protected void onBind(ViewHolder holder, int position, ListModel data) {
            holder.setTextView(R.id.list_tv, data.getTitle());
            ImageLoaderUtils.display(UIUtils.getContext(), holder.getImageView(R.id.list_image), data.getTitleImage());
        }
    }

}
