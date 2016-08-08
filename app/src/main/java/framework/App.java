package framework;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import develop.y.zhzl.BuildConfig;

/**
 * by y on 2016/8/7.
 */
public class App extends Application {

    private static final String K_LOG = "K";

    private final List<Activity> activityList = new ArrayList<>();

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        KLog.init(BuildConfig.LOG_DEBUG, K_LOG);
    }

    private static class AppHolder {
        public static final App APP_UTILS = new App();

    }

    @SuppressWarnings("SameReturnValue")
    public static App getInstance() {
        return AppHolder.APP_UTILS;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

}
