package framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isLoad;
    protected boolean isPrepared;
    protected boolean isVisible;
    protected static final String FRAGMENT_INDEX = "fragment_index";
    protected static final String FRAGMENT_TYPE = "fragment_type";
    protected int pos = 0;
    protected String type = null;
    private View view;

    private BackHandledInterface backHandledInterface = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.backHandledInterface = (BackHandledInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        backHandledInterface.setSelectedFragment(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
            isPrepared = true;
        }
        initById();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
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

    private void onVisible() {
        initData();
    }

    protected abstract void initById();

    protected abstract void initData();

    protected abstract int getLayoutId();

    protected abstract boolean onBackPressed();

    protected void setLoad() {
        isLoad = true;
    }

    public interface BackHandledInterface {

        void setSelectedFragment(BaseFragment selectedFragment);
    }
}

