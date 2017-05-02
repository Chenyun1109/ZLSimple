package develop.y.zhzl.search.presenter;

import android.text.TextUtils;

import com.rxnetwork.bus.RxBus;
import com.rxnetwork.bus.RxBusCallBack;
import com.rxnetwork.manager.RxNetWork;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;
import develop.y.zhzl.search.view.SearchView;
import framework.api.Api;
import framework.data.Constant;
import framework.mvp.PresenterCompat;
import framework.sql.SearchSuffixDb;
import rx.Subscription;

/**
 * by y on 2016/8/7.
 */
public class SearchPresenterImpl extends PresenterCompat<List<ListModel>, SearchView>
        implements SearchPresenter, RxBusCallBack<String> {

    private String suffix = "";
    private final Subscription annalSubscription;

    public SearchPresenterImpl(SearchView mView) {
        super(mView);
        annalSubscription = RxBus.getInstance().toSubscription(Constant.ANNAL_TAG, String.class, this);
    }

    @Override
    protected void onNetWorkNext(List<ListModel> data) {
        getView().hideExplanation();
        getView().adapterRemove();
        getView().rxNetWorkSuccess(data);
        SearchSuffixDb.insert(suffix);
    }


    @Override
    public void netWorkRequest(final String suffix, String limit) {
        this.suffix = suffix;
        getView().hideExplanation();
        if (TextUtils.isEmpty(suffix)) {
            getView().showExplanation();
            getView().suffixIsEmpty();
            return;
        }
        int limits;
        if (TextUtils.isEmpty(limit)) {
            limits = Constant.LIMIT;
        } else {
            limits = Integer.valueOf(limit);
        }
        RxNetWork.
                getInstance().
                getApi(RxNetWork.observable(Api.ZLService.class).getList(suffix, limits, Constant.OFFSET), this);
    }

    @Override
    public void onNext(String data) {
        netWorkRequest(data, "");
//        RxBus.getInstance().unregister(Constant.ANNAL_TAG, annalSubscription);
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
