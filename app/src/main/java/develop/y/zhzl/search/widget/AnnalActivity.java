package develop.y.zhzl.search.widget;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.LinkedList;
import java.util.List;

import develop.y.zhzl.R;
import develop.y.zhzl.main.widget.DarkViewActivity;
import framework.data.Constant;
import framework.sql.GreenDaoDbUtils;
import framework.sql.SearchSuffix;
import framework.utils.RxBus;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/17.
 */
public class AnnalActivity extends DarkViewActivity
        implements XBaseAdapter.OnXBindListener<SearchSuffix>,
        XBaseAdapter.OnItemClickListener<SearchSuffix> {

    private RecyclerView recyclerView;
    private TextView tvAnnal;
    private Toolbar toolbar;

    public static void startIntent() {
        UIUtils.startActivity(AnnalActivity.class);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        toolbar.setTitle(getString(R.string.annal_title));
        XRecyclerViewAdapter<SearchSuffix> mAdapter = new XRecyclerViewAdapter<>();

        if (GreenDaoDbUtils.getSuffixAll().isEmpty()) {
            tvAnnal.setVisibility(View.VISIBLE);
        } else {
            List<SearchSuffix> list = new LinkedList<>();
            list.addAll(GreenDaoDbUtils.getSuffixAll());
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_GRIDVIEW, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(mAdapter
                    .initXData(list)
                    .addRecyclerView(recyclerView)
                    .setLayoutId(R.layout.annal_item)
                    .onXBind(this));
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

    @Override
    public void onXBind(final XViewHolder holder, int position, SearchSuffix searchSuffix) {
        holder.setTextView(R.id.tv_annal, searchSuffix.getSuffix());
        holder.getTextView(R.id.tv_annal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().send(Constant.ANNAL_TAG, holder.getTextView(R.id.tv_annal).getText().toString());
                finish();
            }
        });
    }
}
