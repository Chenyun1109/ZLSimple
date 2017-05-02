package develop.y.zhzl.list.presenter

import com.rxnetwork.manager.RxNetWork

import develop.y.zhzl.list.model.ListModel
import develop.y.zhzl.list.view.IListView
import framework.api.Api
import framework.data.Constant
import framework.mvp.PresenterCompat

/**
 * by y on 2016/8/7.
 */
class ListPresenterImpl(mView: IListView) : PresenterCompat<List<ListModel>, IListView>(mView), ListPresenter {

    override fun onNetWorkNext(data: List<ListModel>) {
        view.removeAllAdapter()
        view.rxNetWorkSuccess(data)
        view.isShowEmptyView()
    }

    override fun netWorkRequest(suffix: String, limit: Int) {
        view.hideEmptyView()
        RxNetWork.getInstance().getApi(RxNetWork.observable(Api.ZLService::class.java).getList(suffix, limit, Constant.OFFSET), this)
    }

}
