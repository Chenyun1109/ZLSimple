package framework.mvp;

/**
 * by y on 2017/2/24
 */

public interface BaseView<T> {

    void rxNetWorkError(Throwable e);

    void rxNetWorkSuccess(T mData);

    void showProgress();

    void hideProgress();
}

