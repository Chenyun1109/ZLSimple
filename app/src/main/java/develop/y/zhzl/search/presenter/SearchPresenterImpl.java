package develop.y.zhzl.search.presenter;

import android.text.TextUtils;

import com.socks.library.KLog;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.search.view.SearchView;
import framework.base.BasePresenterImpl;
import framework.data.Constant;
import framework.network.NetWorkRequest;
import framework.sql.SearchSuffixDb;
import framework.utils.RxBus;
import rx.functions.Action1;

/**
 * by y on 2016/8/7.
 */
public class SearchPresenterImpl extends BasePresenterImpl<SearchView, List<ListModel>>
        implements SearchPresenter {

    private String suffix;

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
    protected void showProgress() {
        view.showProgress();
        view.hideExplanation();
    }

    @Override
    protected void netWorkNext(List<ListModel> listModels) {
        view.adapterRemove();
        SearchSuffixDb.insert(suffix);
        view.setData(listModels);
    }

    @Override
    protected void hideProgress() {
        view.hideProgress();
    }


    @Override
    public void netWorkRequest(final String suffix, String limit) {
        if (TextUtils.isEmpty(suffix)) {
            view.showExplanation();
            view.suffixIsEmpty();
            return;
        }
        int limits;
        if (TextUtils.isEmpty(limit)) {
            limits = Constant.LIMIT;
        } else {
            limits = Integer.valueOf(limit);
        }
        this.suffix = suffix;
        NetWorkRequest.getList(suffix, limits, getSubscriber());
    }

    @Override
    protected void netWorkError() {
        view.netWorkError();
        view.showExplanation();
    }
}
