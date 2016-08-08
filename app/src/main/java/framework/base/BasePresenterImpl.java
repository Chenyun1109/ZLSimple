package framework.base;


import framework.utils.RxUtils;

/**
 * by y on 2016/8/7.
 */
public abstract class BasePresenterImpl<V>
        implements RxUtils.RxBusNetWork {

    protected final V view;

    protected BasePresenterImpl(V view) {
        RxUtils.onBus(this);
        this.view = view;
    }

    @Override
    public void onError() {
        netWorkError();
    }

    protected abstract void netWorkError();


}
