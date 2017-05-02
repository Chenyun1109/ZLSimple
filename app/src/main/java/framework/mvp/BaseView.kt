package framework.mvp

/**
 * by y on 2017/2/24
 */

interface BaseView<in T> {

    fun rxNetWorkError(e: Throwable)

    fun rxNetWorkSuccess(mData: T)

    fun showProgress()

    fun hideProgress()
}

