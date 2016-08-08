package framework.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * by y on 2016/8/7.
 */
public class RxBus {

    private final Subject rxBus;

    private RxBus() {
        rxBus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        return RxbusHolder.rxBus;
    }

    private static class RxbusHolder {
        private static final RxBus rxBus = new RxBus();
    }

    public void sendNetWork(Object object) {
        //noinspection unchecked
        rxBus.onNext(object);
    }

    public <T> Observable<T> toObserverable(final Class<T> eventType) {
        //noinspection unchecked
        return rxBus.ofType(eventType);
    }
}
