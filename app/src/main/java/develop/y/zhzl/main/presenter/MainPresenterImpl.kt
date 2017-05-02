package develop.y.zhzl.main.presenter

import develop.y.zhzl.R
import develop.y.zhzl.main.view.MainView

/**
 * by y on 2016/12/1
 */

class MainPresenterImpl(private val mainView: MainView) : MainPresenter {

    override fun switchId(id: Int) {
        when (id) {
            R.id.search -> {
                mainView.showFAB()
                mainView.notSlide()
            }
            else -> {
                mainView.hideFAB()
                mainView.allowSlide()
            }
        }
        when (id) {
            R.id.zhihu -> mainView.switchZhihu()
            R.id.movie -> mainView.switchMovie()
            R.id.music -> mainView.switchMusic()
            R.id.develop -> mainView.switchDevelop()
            R.id.book -> mainView.switchBook()
            R.id.internet -> mainView.switchInternet()
            R.id.search -> mainView.switchSearch()
            R.id.about -> mainView.switchAbout()
        }
        mainView.closeDrawers()
    }

    override fun switchOnClickId(id: Int) {
        when (id) {
            R.id.iv_head -> mainView.clearDB()
            R.id.iv -> mainView.alterTheme()
        }
    }

}
