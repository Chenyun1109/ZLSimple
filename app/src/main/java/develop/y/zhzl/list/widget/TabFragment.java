package develop.y.zhzl.list.widget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import develop.y.zhzl.R;
import framework.base.BaseFragment;

import static framework.data.Constant.getTabName;

/**
 * by y on 2016/8/7.
 */
public class TabFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

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
    protected void initById() {
        tabLayout = getView(R.id.tab_layout);
        viewPager = getView(R.id.viewPager);
    }

    @Override
    protected void initData() {
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ListFragment.newInstance(position, type);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getTabName(type)[position];
            }

            @Override
            public int getCount() {
                return getTabName(type).length;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }
}
