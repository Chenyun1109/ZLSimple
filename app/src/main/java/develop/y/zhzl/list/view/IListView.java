package develop.y.zhzl.list.view;

import java.util.List;

import develop.y.zhzl.list.model.ListModel;
import framework.base.BaseView;

/**
 * by y on 2016/8/7.
 */
public interface IListView extends BaseView {
    void setData(List<ListModel> data);
}
