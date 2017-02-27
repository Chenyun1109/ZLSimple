package develop.y.zhzl.search.view;

import framework.mvp.BaseView;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;

/**
 * by y on 2016/8/7.
 */
public interface SearchView extends BaseView<List<ListModel>> {
    void adapterRemove();

    void suffixIsEmpty();

    void showExplanation();

    void hideExplanation();
}
