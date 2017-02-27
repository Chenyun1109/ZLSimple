package develop.y.zhzl.list.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static framework.data.Constant.getTabName;

/**
 * by y on 2017/2/27
 */

public class TabAdapter extends FragmentPagerAdapter {

    private String type;

    public TabAdapter(FragmentManager fm, String type) {
        super(fm);
        this.type = type;
    }

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
}
