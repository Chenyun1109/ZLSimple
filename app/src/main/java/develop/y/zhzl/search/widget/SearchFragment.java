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
import develop.y.zhzl.search.presenter.SearchPresenterImpl;
import develop.y.zhzl.search.view.SearchView;
import framework.base.BaseFragment;
import framework.data.Constant;
import framework.utils.ImageLoaderUtils;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class SearchFragment extends BaseFragment<SearchPresenterImpl>
        implements View.OnClickListener, SearchView,
        XBaseAdapter.OnItemClickListener<ListModel>
        , SearchDialog.SearchInterface,
        XBaseAdapter.OnXBindListener<ListModel> {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TextView textView;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    private XRecyclerViewAdapter<ListModel> mAdapter;

    @Override
    protected SearchPresenterImpl initPresenter() {
        return new SearchPresenterImpl(this);
    }

    @Override
    protected void initById() {
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fa_btn);
        recyclerView = getView(R.id.recyclerView);
        textView = getView(R.id.search_explanation);
        progressBar = getView(R.id.progressBar);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    protected void initActivityCreated() {
        showExplanation();
        floatingActionButton.setOnClickListener(this);
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter
                .initXData(new LinkedList<>())
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
    public void showProgress() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int position, ListModel info) {
        DetailActivity.startIntent(info.getSlug());
    }

    @Override
    public void startSearch(String suffix, String limit) {
        adapterRemove();
        presenter.netWorkRequest(suffix, limit);
    }

    @Override
    public void onXBind(XViewHolder holder, int position, ListModel listModel) {
        holder.setTextView(R.id.list_tv, listModel.getTitle());
        ImageLoaderUtils.display(holder.getImageView(R.id.list_image), listModel.getTitleImage());
    }

    @Override
    public void rxNetWorkError(Throwable e) {
        showExplanation();
        UIUtils.SnackBar(floatingActionButton, getString(R.string.suffix_error));
    }

    @Override
    public void rxNetWorkSuccess(List<ListModel> mData) {
        if (!mData.isEmpty()) {
            toolbar.setTitle(mData.get(0).getAuthor().getName());
            mAdapter.addAllData(mData);
        }
    }

}
