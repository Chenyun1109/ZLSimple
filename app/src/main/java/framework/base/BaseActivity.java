package framework.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import framework.App;
import framework.data.Constant;
import framework.utils.RxUtils;
import framework.utils.SpfUtils;
import framework.utils.swipeback.SwipeBackActivity;
import framework.utils.swipeback.SwipeBackLayout;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseActivity extends SwipeBackActivity implements BaseFragment.BackHandledInterface {

    private static Activity activity;
    private BaseFragment baseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        initTheme();
        setContentView(getLayoutId());
        initById();
        initCreate(savedInstanceState);
        setStatusBar();
        App.getInstance().addActivity(activity);
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && isSwipeBackLayout()) {
            swipeBackLayout.setEnableGesture(true);
            swipeBackLayout.setEdgeDp(100);
        } else {
            swipeBackLayout.setEnableGesture(false);
        }
    }

    protected void initTheme() {
        if (getThemeType()) {
            setTheme(Constant.DAY_STYLES);
        } else {
            setTheme(Constant.NIGHT_STYLES);
        }
    }

    public boolean getThemeType() {
        return SpfUtils.isTheme(Constant.DAY);
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.baseFragment = selectedFragment;
    }

    public static Activity getActivity() {
        return activity;
    }

    protected <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) findViewById(id);
    }

    protected void setStatusBar() {
    }

    @SuppressWarnings("UnusedParameters")
    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract int getLayoutId();

    protected abstract boolean isSwipeBackLayout();

    @Override
    public void onBackPressed() {
        if (baseFragment == null || !baseFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe();
    }
}
