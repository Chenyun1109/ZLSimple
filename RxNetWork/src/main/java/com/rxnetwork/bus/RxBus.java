package com.rxnetwork.bus;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.ArrayMap;
import android.util.Log;

import com.rxnetwork.util.RxUtils;

import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;

/**
 * by y on 2017/2/22.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class RxBus {

    private final ArrayMap<Object, Subject<Object, Object>> rxMap;
    private CompositeSubscription compositeSubscription;

    private RxBus() {
        rxMap = new ArrayMap<>();
        compositeSubscription = new CompositeSubscription();
    }

    public static RxBus getInstance() {
        return RxBusHolder.rxBus;
    }

    private static class RxBusHolder {
        private static final RxBus rxBus = new RxBus();
    }

    /**
     * 发送一个带tag,但不携带信息的消息
     *
     * @param tag 标志
     */
    public void send(@NonNull Object tag) {
        send(tag, tag);
    }

    /**
     * 发送一个带tag,携带信息的消息
     *
     * @param tag     标志
     * @param message 内容
     */
    public void send(@NonNull Object tag,
                     @NonNull Object message) {
        if (RxUtils.isEmpty(rxMap, tag)) {
            Log.i((String) tag, "发送消息");
            rxMap.get(tag).onNext(message);
        }
    }

    /**
     * 接受消息
     *
     * @param tag      标志
     * @param service  类型
     * @param callBack 回调
     */
    public <T> void toSubscription(@NonNull final Object tag,
                                   @NonNull Class<T> service,
                                   @NonNull final RxBusCallBack<T> callBack) {
        Subject<Object, Object> subject = rxMap.get(tag);
        if (RxUtils.isEmpty(subject)) {
            subject = new SerializedSubject<>(PublishSubject.create());
            rxMap.put(tag, subject);
        }
        compositeSubscription.add(subject
                .ofType(service)
                .subscribe(
                        new RxBusSubscriber<T>() {
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                callBack.onError(e);
                            }

                            @Override
                            public void onNext(T t) {
                                super.onNext(t);
                                callBack.onNext(t);
                            }
                        }));

    }


    /**
     * 取消订阅
     *
     * @param tag 标志
     */
    public void unregister(@NonNull Object tag) {
        if (RxUtils.isEmpty(rxMap, tag)) {
            rxMap.remove(tag);
        }
    }

    /**
     * 取消所有订阅
     */
    public void unregisterAll() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.clear();
        }
        rxMap.clear();
    }

    private static class RxBusSubscriber<T> extends Subscriber<T> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(T t) {

        }
    }
}
