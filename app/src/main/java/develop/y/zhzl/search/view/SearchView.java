package develop.y.zhzl.search.view;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;
import framework.base.BaseView;

/**
 * by y on 2016/8/7.
 */
public interface SearchView extends BaseView {
    void setData(List<ListModel> data);

    void suffixIsEmpty();

    void showExplanation();

    void hideExplanation();
}
