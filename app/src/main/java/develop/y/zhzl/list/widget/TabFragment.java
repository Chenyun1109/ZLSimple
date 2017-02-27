package develop.y.zhzl.list.widget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import develop.y.zhzl.R;
import framework.base.BaseFragment;
import framework.mvp.PresenterCompat;

/**
 * by y on 2016/8/7.
 */
public class TabFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter adapter;

    public static TabFragment newInstance(String type) {
        TabFragment imageListTabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_INDEX, type);
        imageListTabFragment.setArguments(bundle);
        return imageListTabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle && !bundle.isEmpty()) {
            type = bundle.getString(FRAGMENT_INDEX);
        }
    }

    @Override
    protected PresenterCompat initPresenter() {
        return null;
    }

    @Override
    protected void initById() {
        tabLayout = (TabLayout) getView(R.id.tab_layout);
        viewPager = (ViewPager) getView(R.id.viewPager);
    }

    @Override
    protected void initActivityCreated() {
        adapter = new TabAdapter(getChildFragmentManager(), type);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }
}
