package framework.utils;

import com.socks.library.KLog;

import framework.data.Constant;
import rx.Subscription;
import rx.functions.Action1;

/**
 * by y on 2016/8/7.
 */
public class RxUtils {

    public static Subscription subscription;

    public static void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    public static void onBus(final RxBusNetWork rxBusNetWork) {
        RxBus.getInstance().toObserverable(Constant.NETWORK_ERROR).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                KLog.i(o);
                rxBusNetWork.onError();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                KLog.i(throwable.getMessage());
            }
        });
    }


    public interface RxBusNetWork {
        void onError();
    }

}
