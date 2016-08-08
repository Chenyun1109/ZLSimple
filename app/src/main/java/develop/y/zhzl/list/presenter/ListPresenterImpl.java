package develop.y.zhzl.list.presenter;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.list.view.IListView;
import framework.base.BasePresenterImpl;
import framework.network.MySubscriber;
import framework.network.NetWorkRequest;

/**
 * by y on 2016/8/7.
 */
public class ListPresenterImpl extends BasePresenterImpl<IListView>
        implements ListPresenter {


    public ListPresenterImpl(IListView view) {
        super(view);
    }


    @Override
    public void netWorkRequest(String suffix, int limit) {
        view.showProgress();
        NetWorkRequest.getList(suffix, limit, new MySubscriber<List<ListModel>>() {
            @Override
            public void onNext(List<ListModel> listModel) {
                super.onNext(listModel);
                view.setData(listModel);
                view.hideProgress();
            }
        });
    }

    @Override
    protected void netWorkError() {
        view.hideProgress();
        view.netWorkError();
    }
}
