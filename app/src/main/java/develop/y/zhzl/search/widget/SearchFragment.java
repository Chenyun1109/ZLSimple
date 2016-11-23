package develop.y.zhzl.search.widget;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.LinkedList;
import java.util.List;

import develop.y.zhzl.R;
import develop.y.zhzl.detail.widget.DetailActivity;
import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.search.presenter.SearchPresenter;
import develop.y.zhzl.search.presenter.SearchPresenterImpl;
import develop.y.zhzl.search.view.SearchView;
import framework.base.BaseFragment;
import framework.data.Constant;
import framework.utils.ImageLoaderUtils;
import framework.utils.UIUtils;
import rx.Observable;

/**
 * by y on 2016/8/7.
 */
public class SearchFragment extends BaseFragment
        implements View.OnClickListener, SearchView,
        XBaseAdapter.OnItemClickListener<ListModel>
        , SearchDialog.SearchInterface,
        XBaseAdapter.OnXBindListener<ListModel> {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TextView textView;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    private SearchPresenter searchPresenter;
    private XRecyclerViewAdapter<ListModel> mAdapter;

    @Override
    protected void initById() {
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fa_btn);
        recyclerView = getView(R.id.recyclerView);
        textView = getView(R.id.search_explanation);
        progressBar = getView(R.id.progressBar);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    protected void initData() {
        showExplanation();
        floatingActionButton.setOnClickListener(this);
        searchPresenter = new SearchPresenterImpl(this);
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter
                .initXData(new LinkedList<ListModel>())
                .addRecyclerView(recyclerView)
                .setLayoutId(R.layout.list_item)
                .onXBind(this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fa_btn:
                SearchDialog.start(getActivity(), this);
                break;
        }
    }

    @Override
    public void setData(List<ListModel> data) {
        if (!data.isEmpty()) {
            toolbar.setTitle(data.get(0).getAuthor().getName());
            mAdapter.addAllData(data);
        }
    }

    @Override
    public void adapterRemove() {
        mAdapter.removeAll();
    }

    @Override
    public void suffixIsEmpty() {
        UIUtils.SnackBar(floatingActionButton, getString(R.string.suffix_null));
    }

    @Override
    public void showExplanation() {
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideExplanation() {
        textView.setVisibility(View.GONE);
    }

    @Override
    public void netWorkError() {
        showExplanation();
        UIUtils.SnackBar(floatingActionButton, getString(R.string.suffix_error));
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void viewBindToLifecycle(Observable<List<ListModel>> observable) {
        if (observable != null) {
            observable.compose(this.<List<ListModel>>bindToLifecycle());
        }
    }

    @Override
    public void onItemClick(View view, int position, ListModel info) {
        DetailActivity.startIntent(info.getSlug());
    }

    @Override
    public void startSearch(String suffix, String limit) {
        adapterRemove();
        searchPresenter.netWorkRequest(suffix, limit);
    }

    @Override
    public void onXBind(XViewHolder holder, int position, ListModel listModel) {
        holder.setTextView(R.id.list_tv, listModel.getTitle());
        ImageLoaderUtils.display(UIUtils.getContext(), holder.getImageView(R.id.list_image), listModel.getTitleImage());
    }
}
