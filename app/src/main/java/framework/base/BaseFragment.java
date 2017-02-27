package framework.base;

import framework.mvp.MVPLazyFragment;
import framework.mvp.PresenterCompat;



/**
 * by y on 2016/8/7.
 * <p>
 * 空实现 仅仅为了放置一些通用的变量
 */
public abstract class BaseFragment<
        P extends PresenterCompat> extends MVPLazyFragment<P> {

    protected static final String FRAGMENT_INDEX = "fragment_index";
    protected static final String FRAGMENT_TYPE = "fragment_type";
    protected int pos = 0;
    protected String type = null;

}

