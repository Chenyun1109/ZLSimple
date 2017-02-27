package framework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.rxnetwork.bus.RxBus;
import com.rxnetwork.manager.RxNetWork;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import develop.y.zhzl.BuildConfig;
import framework.api.Api;
import framework.utils.SPUtils;

/**
 * by y on 2016/8/7.
 */
public class App extends Application {

    private static final String K_LOG = "K";

    private final List<Activity> activityList = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        KLog.init(BuildConfig.LOG_DEBUG, K_LOG);
        SPUtils.init(getInstance());
        RxNetWork.getInstance().setBaseUrl(Api.BASE_API);
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
        RxNetWork.getInstance().clearSubscription();
        RxBus.getInstance().unregisterAll();
        RxNetWork.getInstance().clearSubscription();
    }

    public void refreshAllActivity() {
        for (Activity activity : activityList) {
            activity.recreate();
        }
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }
}
