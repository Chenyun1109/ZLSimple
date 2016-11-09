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
public abstract class BasePresenterImpl<V, M> {

    protected final V view;

    protected BasePresenterImpl(V view) {
        this.view = view;
    }

    protected NetWorkSubscriber getSubscriber() {
        return new NetWorkSubscriber();
    }

    private class NetWorkSubscriber extends Subscriber<M> {

        @Override
        public void onStart() {
            super.onStart();
            showProgress();
        }

        @Override
        public void onCompleted() {
            hideProgress();
        }


        @Override
        public void onError(Throwable e) {
            KLog.i(e.toString());
            hideProgress();
            netWorkError();
        }

        @Override
        public void onNext(M t) {
            netWorkNext(t);
        }

    }

    protected abstract void showProgress();

    protected abstract void netWorkNext(M m);

    protected abstract void hideProgress();

    protected abstract void netWorkError();

    public void startNetWork(Observable<M> observable) {
        RxUtils.unsubscribe();
        RxUtils.subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber());
    }
}
