package develop.y.zhzl.list.widget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import develop.y.zhzl.R;
import framework.base.BaseFragment;
import framework.data.Constant;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class TabFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static TabFragment newInstance(String type) {
        TabFragment imageListTabFragment = new TabFragment();
        Bundle bundle = UIUtils.getBundle();
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
        viewPager.setAdapter(new TabAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    public class TabAdapter extends FragmentPagerAdapter {

        private String[] name;

        public TabAdapter(FragmentManager fm) {
            super(fm);
            switch (type) {
                case Constant.ZHIHU:
                    name = UIUtils.getStringArray(R.array.zhihu);
                    break;
                case Constant.MOVIE:
                    name = UIUtils.getStringArray(R.array.movie);
                    break;
                case Constant.MUSIC:
                    name = UIUtils.getStringArray(R.array.music);
                    break;
                case Constant.DEVELOP:
                    name = UIUtils.getStringArray(R.array.develop);
                    break;
                case Constant.BOOK:
                    name = UIUtils.getStringArray(R.array.book);
                    break;
                case Constant.INTERNET:
                    name = UIUtils.getStringArray(R.array.internet);
                    break;
                default:
                    break;
            }
        }

        @Override
        public Fragment getItem(int position) {
            return ListFragment.newInstance(position, type);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return name[position];
        }

        @Override
        public int getCount() {
            return name.length;
        }
    }
}
