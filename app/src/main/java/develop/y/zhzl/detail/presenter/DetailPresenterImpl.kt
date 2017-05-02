package develop.y.zhzl.detail.presenter


import com.rxnetwork.manager.RxNetWork

import develop.y.zhzl.detail.model.DetailModel
import develop.y.zhzl.detail.view.DetailView
import framework.api.Api
import framework.mvp.PresenterCompat

/**
 * by y on 2016/8/7.
 */
class DetailPresenterImpl(mView: DetailView) : PresenterCompat<DetailModel, DetailView>(mView), DetailPresenter {

    override fun onNetWorkNext(data: DetailModel) {
        view.rxNetWorkSuccess(data)
    }

    override fun netWorkRequest(slug: Int) {
        RxNetWork.getInstance().getApi(RxNetWork.observable(Api.ZLService::class.java).getDetail(slug), this)
    }

}
