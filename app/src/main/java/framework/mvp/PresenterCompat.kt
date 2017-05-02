package framework.mvp

import com.rxnetwork.manager.RxNetWorkListener

/**
 * by y on 2017/2/24
 *
 *
 *
 *
 * M :  model
 *
 *
 * V ： View
 */

abstract class PresenterCompat<M, out V : BaseView<*>>(val view: V) : RxNetWorkListener<M> {

    override fun onNetWorkStart() {
        view.showProgress()
    }

    override fun onNetWorkCompleted() {
        view.hideProgress()
    }

    override fun onNetWorkError(e: Throwable) {
        view.hideProgress()
        view.rxNetWorkError(e)
    }

    override fun onNetWorkSuccess(data: M) {
        onNetWorkNext(data)
    }

    protected abstract fun onNetWorkNext(data: M)
}

