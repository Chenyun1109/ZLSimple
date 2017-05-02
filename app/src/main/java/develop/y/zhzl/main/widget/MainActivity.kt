package develop.y.zhzl.main.widget

import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView

import com.rxnetwork.bus.RxBus
import com.rxnetwork.bus.RxBusCallBack
import com.rxnetwork.manager.RxSubscriptionManager

import develop.y.zhzl.R
import develop.y.zhzl.list.widget.TabFragment
import develop.y.zhzl.main.presenter.MainPresenter
import develop.y.zhzl.main.presenter.MainPresenterImpl
import develop.y.zhzl.main.view.MainView
import develop.y.zhzl.search.widget.SearchFragment
import framework.App
import framework.data.Constant
import framework.sql.sqlutil.GreenDaoDbUtils
import framework.utils.CacheUtils
import framework.utils.SPUtils
import framework.utils.StatusBarUtil
import framework.utils.UIUtils
import rx.Subscription

class MainActivity : DarkViewActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainView, RxBusCallBack<String> {

    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var appBarLayout: AppBarLayout? = null
    private var floatingActionButton: FloatingActionButton? = null
    private var layoutParams: AppBarLayout.LayoutParams? = null
    private var imageViewTheme: ImageView? = null
    private var mPresenter: MainPresenter? = null
    private var exitTime: Long = 0
    private var themeSubscription: Subscription? = null

    override fun initCreate() {
        toolbar!!.title = getString(R.string.zhihu)
        setSupportActionBar(toolbar)
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout!!, 0)
        navigationView!!.setNavigationItemSelectedListener(this)
        replaceFragment(TabFragment.newInstance(Constant.ZHIHU))
        layoutParams = appBarLayout!!.getChildAt(0).layoutParams as AppBarLayout.LayoutParams
        mPresenter = MainPresenterImpl(this)
        imageViewTheme!!.setBackgroundResource(if (themeType) R.drawable.day else R.drawable.night)
        themeSubscription = RxBus.getInstance().toSubscription(Constant.THEME_TAG, String::class.java, this)
        allowSlide()
    }

    override fun initById() {
        toolbar = getView(R.id.toolbar)
        drawerLayout = getView(R.id.dl_layout)
        navigationView = getView(R.id.navigationview)
        appBarLayout = getView(R.id.appbar)
        floatingActionButton = getView(R.id.fa_btn)
        navigationView!!.getHeaderView(0).findViewById(R.id.iv_head).setOnClickListener(this)
        imageViewTheme = navigationView!!.getHeaderView(0).findViewById(R.id.iv) as ImageView
        imageViewTheme!!.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun isSwipeBackLayout(): Boolean {
        return false
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            closeDrawers()
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                UIUtils.snackBar(findViewById(R.id.coordinatorLayout), getString(R.string.exit_app))
                exitTime = System.currentTimeMillis()
            } else {
                App.instance.exit()
                super.onBackPressed()
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        RxSubscriptionManager.getInstance().clearSubscription()
        toolbar!!.title = item.title
        mPresenter!!.switchId(item.itemId)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mPresenter!!.switchId(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        mPresenter!!.switchOnClickId(view.id)
    }

    override fun switchZhihu() {
        replaceFragment(TabFragment.newInstance(Constant.ZHIHU))
    }

    override fun switchMovie() {
        replaceFragment(TabFragment.newInstance(Constant.MOVIE))
    }

    override fun switchMusic() {
        replaceFragment(TabFragment.newInstance(Constant.MUSIC))
    }

    override fun switchDevelop() {
        replaceFragment(TabFragment.newInstance(Constant.DEVELOP))
    }

    override fun switchBook() {
        replaceFragment(TabFragment.newInstance(Constant.BOOK))
    }

    override fun switchInternet() {
        replaceFragment(TabFragment.newInstance(Constant.INTERNET))
    }

    override fun switchSearch() {
        replaceFragment(SearchFragment())
    }

    override fun switchAbout() {
        AboutActivity.startIntent()
    }

    override fun showFAB() {
        floatingActionButton!!.visibility = View.VISIBLE
    }

    override fun hideFAB() {
        floatingActionButton!!.visibility = View.GONE
    }

    override fun notSlide() {
        layoutParams!!.scrollFlags = 0
    }

    override fun allowSlide() {
        layoutParams!!.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
    }

    override fun closeDrawers() {
        drawerLayout!!.closeDrawers()
    }

    override fun clearDB() {
        drawerLayout!!.closeDrawers()
        GreenDaoDbUtils.clear()
        UIUtils.snackBar(floatingActionButton!!, getString(R.string.clear_db))
    }

    override fun alterTheme() {
        imageViewTheme!!.setBackgroundResource(if (themeType) R.drawable.day else R.drawable.night)
        setTheme(if (themeType) Constant.NIGHT_STYLES else Constant.DAY_STYLES)
        SPUtils.setTheme(!themeType)
        CacheUtils.instance.put(Constant.BITMAP_CACHE_KEY, UIUtils.captureContent(this))
        TransitionActivity.startIntent()
    }

    override fun onNext(data: String) {
        navigationView!!.menu.findItem(R.id.zhihu).isChecked = true
        RxBus.getInstance().unregister(Constant.THEME_TAG, themeSubscription!!)
    }

    override fun onError(throwable: Throwable) {

    }
}
