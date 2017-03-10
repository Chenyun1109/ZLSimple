package develop.y.zhzl.detail.presenter;


import com.rxnetwork.manager.RxNetWork;

import develop.y.zhzl.detail.model.DetailModel;
import develop.y.zhzl.detail.view.DetailView;
import framework.api.Api;
import framework.mvp.PresenterCompat;

/**
 * by y on 2016/8/7.
 */
public class DetailPresenterImpl extends PresenterCompat<DetailModel, DetailView> implements DetailPresenter {

    public DetailPresenterImpl(DetailView mView) {
        super(mView);
    }

    @Override
    protected void onNetWorkNext(DetailModel data) {
        getView().rxNetWorkSuccess(data);
    }

    @Override
    public void netWorkRequest(int slug) {
        RxNetWork.
                getInstance().
                getApi(RxNetWork.observable(Api.ZLService.class).getDetail(slug), this);
    }

}
