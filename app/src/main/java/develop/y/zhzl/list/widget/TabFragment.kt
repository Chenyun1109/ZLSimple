package develop.y.zhzl.list.widget

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import develop.y.zhzl.R

/**
 * by y on 2016/8/7.
 */
class TabFragment : Fragment() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var adapter: TabAdapter? = null

    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (null != bundle && !bundle.isEmpty) {
            type = bundle.getString("index")
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater!!.inflate(R.layout.fragment_tab, null)
        tabLayout = inflate.findViewById(R.id.tab_layout) as TabLayout
        viewPager = inflate.findViewById(R.id.viewPager) as ViewPager
        return inflate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TabAdapter(childFragmentManager, type!!)
        viewPager!!.adapter = adapter
        tabLayout!!.setupWithViewPager(viewPager)
    }

    companion object {

        fun newInstance(type: String): TabFragment {
            val imageListTabFragment = TabFragment()
            val bundle = Bundle()
            bundle.putString("index", type)
            imageListTabFragment.arguments = bundle
            return imageListTabFragment
        }
    }

}
