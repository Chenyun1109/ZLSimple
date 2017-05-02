package develop.y.zhzl.list.widget

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import framework.data.Constant.getTabName

/**
 * by y on 2017/2/27
 */

class TabAdapter(fm: FragmentManager, private val type: String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ListFragment.newInstance(position, type)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return getTabName(type)[position]
    }

    override fun getCount(): Int {
        return getTabName(type).size
    }
}
