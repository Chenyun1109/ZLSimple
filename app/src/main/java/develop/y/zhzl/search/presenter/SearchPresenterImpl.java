package develop.y.zhzl.search.presenter;

import com.socks.library.KLog;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.search.view.SearchView;
import framework.base.BasePresenterImpl;
import framework.data.Constant;
import framework.network.MySubscriber;
import framework.network.NetWorkRequest;
import framework.sql.SearchSuffixDb;
import framework.utils.RxBus;
import rx.functions.Action1;

/**
 * by y on 2016/8/7.
 */
public class SearchPresenterImpl extends BasePresenterImpl<SearchView>
        implements SearchPresenter {

    public SearchPresenterImpl(SearchView view) {
        super(view);
        RxBus.getInstance().toObserverable(Constant.ANNAL_TAG).subscribe(new Action1<Object>() {
            @Override
            public void call(Object object) {
                netWorkRequest((String) object, "");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                KLog.i(throwable.toString());
            }
        });

    }


    @Override
    public void netWorkRequest(final String suffix, String limit) {
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
//        view.adapterRemove();
        view.showProgress();
        NetWorkRequest.getList(suffix, limits, new MySubscriber<List<ListModel>>() {
            @Override
            public void onNext(List<ListModel> listModels) {
                super.onNext(listModels);
                SearchSuffixDb.insert(suffix);
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
