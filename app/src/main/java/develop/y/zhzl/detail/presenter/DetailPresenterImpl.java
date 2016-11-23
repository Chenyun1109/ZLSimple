package develop.y.zhzl.detail.presenter;


import develop.y.zhzl.detail.model.DetailModel;
import develop.y.zhzl.detail.view.DetailView;
import framework.base.BasePresenterImpl;
import framework.network.NetWork;

/**
 * by y on 2016/8/7.
 */
public class DetailPresenterImpl extends BasePresenterImpl<DetailView, DetailModel> implements DetailPresenter {


    public DetailPresenterImpl(DetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int slug) {
        startNetWork(observable = NetWork.getZlApi().getDetail(slug));
    }


    @Override
    protected void netWorkNext(DetailModel detailModel) {
        view.setData(detailModel);
    }
}
