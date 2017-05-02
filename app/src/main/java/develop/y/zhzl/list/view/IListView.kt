package develop.y.zhzl.list.view

import develop.y.zhzl.list.model.ListModel
import framework.mvp.BaseView

/**
 * by y on 2016/8/7.
 */
interface IListView : BaseView<List<ListModel>> {

    fun hideEmptyView()

    fun isShowEmptyView()

    fun removeAllAdapter()
}
