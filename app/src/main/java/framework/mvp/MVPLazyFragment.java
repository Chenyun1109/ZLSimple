package framework.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * by y on 2017/2/25.
 * <p>
 * 懒加载 BaseFragment
 */

public abstract class MVPLazyFragment<
        P extends PresenterCompat> extends Fragment {

    protected boolean isLoad;
    protected boolean isPrepared;
    protected boolean isVisible;
    protected View view;
    protected P presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = initPresenter();
        if (view == null) {
            view = getLayoutInflater(savedInstanceState).inflate(getLayoutId(), null);
            isPrepared = true;
        }
        initById();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActivityCreated();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }


    protected <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) view.findViewById(id);
    }

    protected abstract P initPresenter();

    private void onVisible() {
        initActivityCreated();
    }

    protected abstract void initById();

    protected abstract void initActivityCreated();

    protected abstract int getLayoutId();

    protected void setLoad() {
        isLoad = true;
    }
}

