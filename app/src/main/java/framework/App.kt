package framework

import android.app.Activity
import android.app.Application
import android.content.Context
import com.rxnetwork.bus.RxBus
import com.rxnetwork.manager.RxNetWork
import com.rxnetwork.manager.RxSubscriptionManager
import com.socks.library.KLog
import develop.y.zhzl.BuildConfig
import framework.api.Api
import framework.utils.SPUtils
import java.util.*

/**
 * by y on 2016/8/7.
 */
class App : Application() {
    private val activityList = ArrayList<Activity>()

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        KLog.init(BuildConfig.LOG_DEBUG, K_LOG)
        SPUtils.init(instance)
        RxNetWork.getInstance().setBaseUrl(Api.BASE_API)
    }

    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    fun exit() {
        val size = activityList.size
        for (i in 0..size - 1) {
            activityList[i].finish()
        }
        RxSubscriptionManager.getInstance().clearSubscription()
        RxBus.getInstance().unregisterAll()
    }

    fun refreshAllActivity() {
        val size = activityList.size
        for (i in 0..size - 1) {
            activityList[i].recreate()
        }
    }

    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    companion object {

        private val K_LOG = "K"
        private var context: Context? = null

        val instance: App
            get() = (context as App?)!!
    }
}
