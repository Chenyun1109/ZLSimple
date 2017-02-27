package develop.y.zhzl.list.presenter;

import com.rxnetwork.manager.RxNetWork;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.list.view.IListView;
import framework.api.Api;
import framework.data.Constant;
import framework.mvp.PresenterCompat;

/**
 * by y on 2016/8/7.
 */
public class ListPresenterImpl extends PresenterCompat<List<ListModel>, IListView>
        implements ListPresenter {


    public ListPresenterImpl(IListView mView) {
        super(mView);
    }

    @Override
    protected void onNetWorkNext(List<ListModel> data) {
        getView().removeAllAdapter();
        getView().rxNetWorkSuccess(data);
        getView().isShowEmptyView();
    }

    @Override
    public void netWorkRequest(String suffix, int limit) {
        getView().hideEmptyView();
        RxNetWork.
                getInstance().
                getApi(RxNetWork.observable(Api.ZLService.class).getList(suffix, limit, Constant.OFFSET), this);
    }

}
