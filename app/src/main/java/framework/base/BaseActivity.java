package framework.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import develop.y.zhzl.R;
import framework.App;
import framework.data.Constant;
import framework.utils.SpfUtils;
import framework.utils.swipeback.SwipeBackActivity;
import framework.utils.swipeback.SwipeBackLayout;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(getLayoutId());
        initById();
        initCreate(savedInstanceState);
        App.getInstance().addActivity(this);
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


    protected <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) findViewById(id);
    }

    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract int getLayoutId();

    protected abstract boolean isSwipeBackLayout();

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }
}
