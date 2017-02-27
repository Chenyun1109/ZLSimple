package framework.mvp;

import com.rxnetwork.manager.RxNetWorkListener;

/**
 * by y on 2017/2/24
 * <p>
 * <p>
 * M :  model
 * <p>
 * V ： View
 */

public abstract class PresenterCompat<M, V extends BaseView>
        implements RxNetWorkListener<M> {

    private V mView;

    public PresenterCompat(V mView) {
        this.mView = mView;
    }

    public V getView() {
        return mView;
    }

    @Override
    public void onNetWorkStart() {
        getView().showProgress();
    }

    @Override
    public void onNetWorkCompleted() {
        getView().hideProgress();
    }

    @Override
    public void onNetWorkError(Throwable e) {
        getView().hideProgress();
        getView().rxNetWorkError(e);
    }

    @Override
    public void onNetWorkSuccess(M data) {
        onNetWorkNext(data);
    }

    protected abstract void onNetWorkNext(M data);
}

