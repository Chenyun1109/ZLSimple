package framework.network;

import com.socks.library.KLog;

import framework.data.Constant;
import framework.utils.RxBus;
import rx.Subscriber;

/**
 * by y on 2016/8/7.
 */
public class MySubscriber<T> extends Subscriber<T> {


    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        KLog.i(e.getMessage());
        RxBus.getInstance().sendNetWork(Constant.NETWORK_ERROR);
    }

    @Override
    public void onNext(T t) {
    }

}
