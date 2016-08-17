package framework;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import develop.y.zhzl.BuildConfig;
import framework.utils.SpfUtils;

/**
 * by y on 2016/8/7.
 */
public class App extends Application {

    private static final String K_LOG = "K";

    private final List<Activity> activityList = new ArrayList<>();

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        KLog.init(BuildConfig.LOG_DEBUG, K_LOG);
        SpfUtils.init(getInstance());
    }

    public static App getInstance() {
        return (App) context;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public void refreshAllActivity() {
        for (Activity activity : activityList) {
            activity.recreate();
        }
    }
}
