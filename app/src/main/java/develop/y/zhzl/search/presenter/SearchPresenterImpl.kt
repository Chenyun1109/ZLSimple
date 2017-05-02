package develop.y.zhzl.search.presenter

import android.text.TextUtils

import com.rxnetwork.bus.RxBus
import com.rxnetwork.bus.RxBusCallBack
import com.rxnetwork.manager.RxNetWork

import develop.y.zhzl.list.model.ListModel
import develop.y.zhzl.search.view.SearchView
import framework.api.Api
import framework.data.Constant
import framework.mvp.PresenterCompat
import framework.sql.sqlutil.SearchSuffixDb
import rx.Subscription

/**
 * by y on 2016/8/7.
 */
class SearchPresenterImpl(mView: SearchView) : PresenterCompat<List<ListModel>, SearchView>(mView), SearchPresenter, RxBusCallBack<String> {

    private var suffix = ""
    private val annalSubscription: Subscription = RxBus.getInstance().toSubscription(Constant.ANNAL_TAG, String::class.java, this)

    override fun onNetWorkNext(data: List<ListModel>) {
        view.hideExplanation()
        view.adapterRemove()
        view.rxNetWorkSuccess(data)
        SearchSuffixDb.insert(suffix)
    }


    override fun netWorkRequest(suffix: String, limit: String) {
        this.suffix = suffix
        view.hideExplanation()
        if (TextUtils.isEmpty(suffix)) {
            view.showExplanation()
            view.suffixIsEmpty()
            return
        }
        val limits: Int
        if (TextUtils.isEmpty(limit)) {
            limits = Constant.LIMIT
        } else {
            limits = Integer.valueOf(limit)!!
        }
        RxNetWork.getInstance().getApi(RxNetWork.observable(Api.ZLService::class.java).getList(suffix, limits, Constant.OFFSET), this)
    }

    override fun onNext(data: String) {
        netWorkRequest(data, "")
        //        RxBus.getInstance().unregister(Constant.ANNAL_TAG, annalSubscription);
    }

    override fun onError(throwable: Throwable) {

    }
}
