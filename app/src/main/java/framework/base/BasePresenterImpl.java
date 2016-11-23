package framework.base;


import com.socks.library.KLog;

import framework.utils.RxUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by y on 2016/8/7.
 */
public abstract class BasePresenterImpl<V extends BaseView<M>, M> {

    protected final V view;
    protected Observable<M> observable;

    protected BasePresenterImpl(V view) {
        this.view = view;
    }

    private NetWorkSubscriber getSubscriber() {
        return new NetWorkSubscriber();
    }

    public class NetWorkSubscriber extends Subscriber<M> {

        @Override
        public void onStart() {
            super.onStart();
            view.showProgress();
        }

        @Override
        public void onCompleted() {
            view.hideProgress();
            view.viewBindToLifecycle(observable);
        }


        @Override
        public void onError(Throwable e) {
            KLog.i(e.toString());
            view.hideProgress();
            view.netWorkError();
            view.viewBindToLifecycle(observable);
        }

        @Override
        public void onNext(M t) {
            netWorkNext(t);
        }

    }

    protected abstract void netWorkNext(M m);

    protected void startNetWork(Observable<M> observable) {
        RxUtils.unsubscribe();
        RxUtils.subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber());
    }
}
