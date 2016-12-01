package develop.y.zhzl.main.presenter;

import develop.y.zhzl.R;
import develop.y.zhzl.main.view.MainView;

/**
 * by y on 2016/12/1
 */

public class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void switchId(int id) {
        switch (id) {
            case R.id.search:
                mainView.showFAB();
                mainView.notSlide();
                break;
            default:
                mainView.hideFAB();
                mainView.allowSlide();
                break;
        }
        switch (id) {
            case R.id.zhihu:
                mainView.switchZhihu();
                break;
            case R.id.movie:
                mainView.switchMovie();
                break;
            case R.id.music:
                mainView.switchMusic();
                break;
            case R.id.develop:
                mainView.switchDevelop();
                break;
            case R.id.book:
                mainView.switchBook();
                break;
            case R.id.internet:
                mainView.switchInternet();
                break;
            case R.id.search:
                mainView.switchSearch();
                break;
            case R.id.about:
                mainView.switchAbout();
                break;
        }
        mainView.closeDrawers();
    }

    @Override
    public void switchOnClickId(int id) {
        switch (id) {
            case R.id.iv_head:
                mainView.clearDB();
                break;
            case R.id.iv:
                mainView.alterTheme();
                break;
        }
    }
}
