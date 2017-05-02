package develop.y.zhzl.main.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.rxnetwork.bus.RxBus;
import com.rxnetwork.bus.RxBusCallBack;
import com.rxnetwork.manager.RxSubscriptionManager;

import develop.y.zhzl.R;
import develop.y.zhzl.list.widget.TabFragment;
import develop.y.zhzl.main.presenter.MainPresenter;
import develop.y.zhzl.main.presenter.MainPresenterImpl;
import develop.y.zhzl.main.view.MainView;
import develop.y.zhzl.search.widget.SearchFragment;
import framework.App;
import framework.data.Constant;
import framework.sql.GreenDaoDbUtils;
import framework.utils.CacheUitls;
import framework.utils.SPUtils;
import framework.utils.StatusBarUtil;
import framework.utils.UIUtils;
import rx.Subscription;

public class MainActivity extends DarkViewActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        MainView,
        RxBusCallBack<String> {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private FloatingActionButton floatingActionButton;
    private AppBarLayout.LayoutParams layoutParams;
    private ImageView imageViewTheme;
    private MainPresenter mPresenter;
    private long exitTime = 0;
    private Subscription themeSubscription;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        toolbar.setTitle(getString(R.string.zhihu));
        setSupportActionBar(toolbar);
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, 0);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(TabFragment.newInstance(Constant.ZHIHU));
        layoutParams = (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
        mPresenter = new MainPresenterImpl(this);
        imageViewTheme.setBackgroundResource(getThemeType() ? R.drawable.day : R.drawable.night);
        themeSubscription = RxBus.getInstance().toSubscription(Constant.THEME_TAG, String.class, this);
        allowSlide();
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        drawerLayout = getView(R.id.dl_layout);
        navigationView = getView(R.id.navigationview);
        appBarLayout = getView(R.id.appbar);
        floatingActionButton = getView(R.id.fa_btn);
        navigationView.getHeaderView(0).findViewById(R.id.iv_head).setOnClickListener(this);
        imageViewTheme = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv);
        imageViewTheme.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isSwipeBackLayout() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawers();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                UIUtils.SnackBar(findViewById(R.id.coordinatorLayout), getString(R.string.exit_app));
                exitTime = System.currentTimeMillis();
            } else {
                App.getInstance().exit();
                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        RxSubscriptionManager.getInstance().clearSubscription();
        toolbar.setTitle(item.getTitle());
        mPresenter.switchId(item.getItemId());
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.switchId(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        mPresenter.switchOnClickId(view.getId());
    }

    @Override
    public void switchZhihu() {
        replaceFragment(TabFragment.newInstance(Constant.ZHIHU));
    }

    @Override
    public void switchMovie() {
        replaceFragment(TabFragment.newInstance(Constant.MOVIE));
    }

    @Override
    public void switchMusic() {
        replaceFragment(TabFragment.newInstance(Constant.MUSIC));
    }

    @Override
    public void switchDevelop() {
        replaceFragment(TabFragment.newInstance(Constant.DEVELOP));
    }

    @Override
    public void switchBook() {
        replaceFragment(TabFragment.newInstance(Constant.BOOK));
    }

    @Override
    public void switchInternet() {
        replaceFragment(TabFragment.newInstance(Constant.INTERNET));
    }

    @Override
    public void switchSearch() {
        replaceFragment(new SearchFragment());
    }

    @Override
    public void switchAbout() {
        AboutActivity.startIntent();
    }

    @Override
    public void showFAB() {
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFAB() {
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public void notSlide() {
        layoutParams.setScrollFlags(0);
    }

    @Override
    public void allowSlide() {
        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
    }

    @Override
    public void closeDrawers() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void clearDB() {
        drawerLayout.closeDrawers();
        GreenDaoDbUtils.clear();
        UIUtils.SnackBar(floatingActionButton, getString(R.string.clear_db));
    }

    @Override
    public void alterTheme() {
        imageViewTheme.setBackgroundResource(getThemeType() ? R.drawable.day : R.drawable.night);
        setTheme(getThemeType() ? Constant.NIGHT_STYLES : Constant.DAY_STYLES);
        SPUtils.setTheme(!getThemeType());
        CacheUitls.getInstance().put(Constant.BITMAP_CACHE_KEY, UIUtils.captureContent(this));
        TransitionActivity.startIntent();
    }

    @Override
    public void onNext(String data) {
        navigationView.getMenu().findItem(R.id.zhihu).setChecked(true);
        RxBus.getInstance().unregister(Constant.THEME_TAG, themeSubscription);
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
