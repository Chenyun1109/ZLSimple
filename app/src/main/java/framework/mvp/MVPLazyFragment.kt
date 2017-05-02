package framework.mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * by y on 2017/2/25.
 *
 *
 * 懒加载 BaseFragment
 */

abstract class MVPLazyFragment<P : PresenterCompat<*, *>> : Fragment() {

    protected var lazyIsLoad: Boolean = false
    protected var lazyIsPrepared: Boolean = false
    protected var lazyIsVisible: Boolean = false
    protected var mView: View? = null
    protected var mPresenter: P  = initPresenter()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = getLayoutInflater(savedInstanceState).inflate(layoutId, null)
            lazyIsPrepared = true
        }
        initById()
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initActivityCreated()
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            lazyIsVisible = true
            onVisible()
        } else {
            lazyIsVisible = false
        }
    }


    protected fun <T : View> getView(id: Int): T {
        return mView!!.findViewById(id) as T
    }

    protected abstract fun initPresenter(): P

    private fun onVisible() {
        initActivityCreated()
    }

    protected abstract fun initById()

    protected abstract fun initActivityCreated()

    protected abstract val layoutId: Int

    protected fun setLoad() {
        lazyIsLoad = true
    }
}

