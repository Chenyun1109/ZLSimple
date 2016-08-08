package develop.y.zhzl.search.widget;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import develop.y.zhzl.R;
import develop.y.zhzl.detail.widget.DetailActivity;
import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.search.presenter.SearchPresenter;
import develop.y.zhzl.search.presenter.SearchPresenterImpl;
import develop.y.zhzl.search.view.SearchView;
import framework.base.BaseFragment;
import framework.base.BaseRecyclerViewAdapter;
import framework.data.Constant;
import framework.utils.ImageLoaderUtils;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class SearchFragment extends BaseFragment
        implements View.OnClickListener, SearchView,
        BaseRecyclerViewAdapter.OnItemClickListener<ListModel>
        , SearchDialog.SearchInterface {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TextView textView;
    private ProgressBar progressBar;

    private SearchPresenter searchPresenter;
    private SearchAdapter adapter;

    @Override
    protected View initView(Bundle savedInstanceState) {
        return UIUtils.getInflate(R.layout.fragment_search);
    }

    @Override
    protected void initById() {
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fa_btn);
        recyclerView = getView(R.id.recyclerView);
        textView = getView(R.id.search_explanation);
        progressBar = getView(R.id.progressBar);
    }

    @Override
    protected void initData() {
        showExplanation();
        floatingActionButton.setOnClickListener(this);
        searchPresenter = new SearchPresenterImpl(this);

        List<ListModel> list = new LinkedList<>();
        adapter = new SearchAdapter(list);
        adapter.setOnItemClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fa_btn:
                SearchDialog.startSearch(getActivity(), this);
                break;
        }
    }

    @Override
    public void setData(List<ListModel> data) {
        if (!data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void suffixIsEmpty() {
        UIUtils.SnackBar(floatingActionButton, UIUtils.getString(R.string.suffix_null));
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
        UIUtils.SnackBar(floatingActionButton, UIUtils.getString(R.string.suffix_error));
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
    public void onItemClick(View view, int position, ListModel info) {
        DetailActivity.startIntent(info.getSlug());
    }

    @Override
    public void startSearch(String suffix, String limit) {
        adapter.removeAll();
        searchPresenter.netWorkRequest(suffix, limit);
    }

    public class SearchAdapter extends BaseRecyclerViewAdapter<ListModel> {

        public SearchAdapter(List<ListModel> mDatas) {
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
