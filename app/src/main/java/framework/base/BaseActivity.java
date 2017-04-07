package framework.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.backlayout.SwipeBackActivity;
import com.backlayout.SwipeBackLayout;

import develop.y.zhzl.R;
import framework.App;
import framework.data.Constant;
import framework.utils.SPUtils;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseActivity extends SwipeBackActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getThemeType() ? Constant.DAY_STYLES : Constant.NIGHT_STYLES);
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

    public boolean getThemeType() {
        return SPUtils.isTheme(Constant.DAY);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
    }
}
