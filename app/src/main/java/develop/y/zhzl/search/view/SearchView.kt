package develop.y.zhzl.search.view

import develop.y.zhzl.list.model.ListModel
import framework.mvp.BaseView

/**
 * by y on 2016/8/7.
 */
interface SearchView : BaseView<List<ListModel>> {
    fun adapterRemove()

    fun suffixIsEmpty()

    fun showExplanation()

    fun hideExplanation()
}
