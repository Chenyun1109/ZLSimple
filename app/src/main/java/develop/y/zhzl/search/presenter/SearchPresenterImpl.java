package develop.y.zhzl.search.presenter;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.search.view.SearchView;
import framework.base.BasePresenterImpl;
import framework.data.Constant;
import framework.network.MySubscriber;
import framework.network.NetWorkRequest;

/**
 * by y on 2016/8/7.
 */
public class SearchPresenterImpl extends BasePresenterImpl<SearchView>
        implements SearchPresenter {

    public SearchPresenterImpl(SearchView view) {
        super(view);
    }


    @Override
    public void netWorkRequest(String suffix, String limit) {
        view.hideExplanation();
        if (suffix.isEmpty()) {
            view.showExplanation();
            view.suffixIsEmpty();
            return;
        }
        int limits;
        if (limit.isEmpty()) {
            limits = Constant.LIMIT;
        } else {
            limits = Integer.valueOf(limit);
        }
        view.showProgress();
        NetWorkRequest.getList(suffix, limits, new MySubscriber<List<ListModel>>() {
            @Override
            public void onNext(List<ListModel> listModels) {
                super.onNext(listModels);
                view.setData(listModels);
                view.hideProgress();
            }
        });
    }

    @Override
    protected void netWorkError() {
        view.showExplanation();
        view.netWorkError();
        view.hideProgress();
    }
}
