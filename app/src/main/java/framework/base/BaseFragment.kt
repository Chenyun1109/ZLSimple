package framework.base

import framework.mvp.MVPLazyFragment
import framework.mvp.PresenterCompat


/**
 * by y on 2016/8/7.
 *
 *
 * 空实现 仅仅为了放置一些通用的变量
 */
abstract class BaseFragment<P : PresenterCompat<*, *>> : MVPLazyFragment<P>() {
    protected var pos = 0
    protected var type: String? = null

    protected val FRAGMENT_INDEX = "fragment_index"
    protected val FRAGMENT_TYPE = "fragment_type"
}

