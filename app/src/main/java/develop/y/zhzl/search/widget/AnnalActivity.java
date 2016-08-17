package develop.y.zhzl.search.widget;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import develop.y.zhzl.R;
import develop.y.zhzl.SearchSuffix;
import develop.y.zhzl.main.DarkViewActivity;
import framework.base.BaseRecyclerViewAdapter;
import framework.data.Constant;
import framework.sql.GreenDaoDbUtils;
import framework.utils.RxBus;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/17.
 */
public class AnnalActivity extends DarkViewActivity implements BaseRecyclerViewAdapter.OnItemClickListener<SearchSuffix> {

    private RecyclerView recyclerView;
    private TextView tvAnnal;
    private Toolbar toolbar;

    public static void startIntent() {
        UIUtils.startActivity(AnnalActivity.class);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        toolbar.setTitle(UIUtils.getString(R.string.annal_title));
        if (GreenDaoDbUtils.getSuffixAll().isEmpty()) {
            tvAnnal.setVisibility(View.VISIBLE);
        } else {
            List<SearchSuffix> list = new LinkedList<>();

            AnnalAdapter annalAdapter = new AnnalAdapter(list);
            annalAdapter.setOnItemClickListener(this);
            annalAdapter.addAll(GreenDaoDbUtils.getSuffixAll());

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_GRIDVIEW, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(annalAdapter);
        }
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        tvAnnal = getView(R.id.is_tv_annal);
        toolbar = getView(R.id.toolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annal;
    }

    @Override
    protected boolean isSwipeBackLayout() {
        return true;
    }

    @Override
    public void onItemClick(View view, int position, SearchSuffix info) {
    }

    public class AnnalAdapter extends BaseRecyclerViewAdapter<SearchSuffix> {

        public AnnalAdapter(List<SearchSuffix> mDatas) {
            super(mDatas);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.annal_item;
        }

        @Override
        protected void onBind(final ViewHolder holder, int position, SearchSuffix data) {
            holder.setTextView(R.id.tv_annal, data.getSuffix());
            holder.get(R.id.tv_annal).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RxBus.getInstance().send(Constant.ANNAL_TAG, holder.getTextView(R.id.tv_annal).getText().toString());
                    finish();
                }
            });
        }
    }

}
