package develop.y.zhzl.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import develop.y.zhzl.R;
import develop.y.zhzl.list.widget.TabFragment;
import develop.y.zhzl.search.widget.SearchFragment;
import framework.App;
import framework.data.Constant;
import framework.utils.CacheUitls;
import framework.utils.SpfUtils;
import framework.utils.StatusBarUtil;
import framework.utils.UIUtils;

public class MainActivity extends DarkViewActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static boolean BACK_EXIT = false;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private FloatingActionButton floatingActionButton;
    private AppBarLayout.LayoutParams layoutParams;
    private ImageView imageViewTheme;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        toolbar.setTitle(UIUtils.getString(R.string.zhihu));
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(TabFragment.newInstance(Constant.ZHIHU));
        layoutParams = (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);


        if (SpfUtils.isTheme(Constant.DAY)) {
            imageViewTheme.setBackgroundResource(R.drawable.day);
        } else {
            imageViewTheme.setBackgroundResource(R.drawable.night);
        }
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, 0);
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
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (BACK_EXIT) {
                super.onBackPressed();
                App.getInstance().exit();
                return;
            }
            BACK_EXIT = true;
            UIUtils.SnackBar(floatingActionButton, UIUtils.getString(R.string.exit_app));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BACK_EXIT = false;
                }
            }, 2000);
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        toolbar.setTitle(item.getTitle());

        switch (item.getItemId()) {
            case R.id.zhihu:
                replaceFragment(TabFragment.newInstance(Constant.ZHIHU));
                break;
            case R.id.movie:
                replaceFragment(TabFragment.newInstance(Constant.MOVIE));
                break;
            case R.id.music:
                replaceFragment(TabFragment.newInstance(Constant.MUSIC));
                break;
            case R.id.develop:
                replaceFragment(TabFragment.newInstance(Constant.DEVELOP));
                break;
            case R.id.book:
                replaceFragment(TabFragment.newInstance(Constant.BOOK));
                break;
            case R.id.internet:
                replaceFragment(TabFragment.newInstance(Constant.INTERNET));
                break;
            case R.id.search:
                replaceFragment(new SearchFragment());
                break;
            default:
                break;
        }
        switch (item.getItemId()) {
            case R.id.search:
                showFAB();
                layoutParams.setScrollFlags(0);
                break;
            default:
                hideFAB();
                layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                AboutActivity.startIntent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showFAB() {
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    private void hideFAB() {
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                drawerLayout.closeDrawers();
                UIUtils.SnackBar(floatingActionButton, getString(R.string.head_image));
                break;
            case R.id.iv:
                if (getThemeType()) {
                    imageViewTheme.setBackgroundResource(R.drawable.night);
                    setTheme(Constant.NIGHT_STYLES);
                    SpfUtils.setTheme(Constant.NIGHT);
                } else {
                    imageViewTheme.setBackgroundResource(R.drawable.day);
                    setTheme(Constant.DAY_STYLES);
                    SpfUtils.setTheme(Constant.DAY);
                }
                CacheUitls.getInstance().put(Constant.BITMAP_CACHE_KEY, UIUtils.captureContent(this));
                TransitionActivity.startIntent();
                break;
        }
    }
}
