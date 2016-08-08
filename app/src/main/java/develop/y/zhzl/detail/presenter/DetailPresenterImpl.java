package develop.y.zhzl.detail.presenter;


import develop.y.zhzl.detail.model.DetailModel;
import develop.y.zhzl.detail.view.DetailView;
import framework.base.BasePresenterImpl;
import framework.network.MySubscriber;
import framework.network.NetWorkRequest;

/**
 * by y on 2016/8/7.
 */
public class DetailPresenterImpl extends BasePresenterImpl<DetailView> implements DetailPresenter {


    public DetailPresenterImpl(DetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int slug) {
        view.showProgress();
        NetWorkRequest.getDetail(slug, new MySubscriber<DetailModel>() {
            @Override
            public void onNext(DetailModel detailModel) {
                super.onNext(detailModel);
                view.setData(detailModel);
                view.hideProgress();
            }
        });
    }

    @Override
    protected void netWorkError() {
        view.netWorkError();
        view.hideProgress();
    }
}
