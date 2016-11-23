package develop.y.zhzl.list.presenter;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.list.view.IListView;
import framework.base.BasePresenterImpl;
import framework.data.Constant;
import framework.network.NetWork;

/**
 * by y on 2016/8/7.
 */
public class ListPresenterImpl extends BasePresenterImpl<IListView, List<ListModel>>
        implements ListPresenter {


    public ListPresenterImpl(IListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String suffix, int limit) {
        view.hideEmptyView();
        startNetWork(observable = NetWork.getZlApi().getList(suffix, limit, Constant.OFFSET));
    }


    @Override
    protected void netWorkNext(List<ListModel> listModels) {
        view.removeAllAdapter();
        view.setData(listModels);
        view.isShowEmptyView();
    }
}
