package framework.base;


import rx.Observable;

/**
 * by y on 2016/8/7.
 */
public interface BaseView<T> {
    void setData(T data);

    void netWorkError();

    void showProgress();

    void hideProgress();

    void viewBindToLifecycle(Observable<T> observable);
}
