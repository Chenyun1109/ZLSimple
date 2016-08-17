package framework.network;


import com.socks.library.KLog;

import java.util.List;

import develop.y.zhzl.detail.model.DetailModel;
import develop.y.zhzl.list.model.ListModel;
import framework.data.Constant;
import framework.utils.RxUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by y on 2016/8/7.
 */
public class NetWorkRequest {

    public static void getList(String suffix, int limit, Subscriber<List<ListModel>> subscriber) {
        RxUtils.unsubscribe();
        RxUtils.subscription = NetWork.getZlApi().getList(suffix, limit, Constant.OFFSET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getDetail(int slug, Subscriber<DetailModel> subscriber) {
        RxUtils.unsubscribe();
        RxUtils.subscription = NetWork.getZlApi().getDetail(slug)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
