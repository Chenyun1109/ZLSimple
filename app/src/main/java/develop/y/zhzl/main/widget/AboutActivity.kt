package develop.y.zhzl.main.widget

import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.widget.ImageView

import develop.y.zhzl.R
import framework.utils.StatusBarUtil
import framework.utils.UIUtils

/**
 * by y on 2016/8/7.
 */
class AboutActivity : DarkViewActivity() {

    private var imageView: ImageView? = null
    private var collapsingToolbar: CollapsingToolbarLayout? = null
    private var floatingActionButton: FloatingActionButton? = null

    override fun initCreate() {
        StatusBarUtil.setTranslucentForImageView(this, imageView!!)
        collapsingToolbar!!.title = getString(R.string.about)
        collapsingToolbar!!.setCollapsedTitleTextColor(UIUtils.getColor(R.color.white))
        collapsingToolbar!!.setExpandedTitleColor(UIUtils.getColor(R.color.purple))
        floatingActionButton!!.setOnClickListener { UIUtils.share(this@AboutActivity, getString(R.string.share_about)) }
    }

    override fun initById() {
        collapsingToolbar = getView(R.id.collapsing_toolbar)
        imageView = getView(R.id.image)
        floatingActionButton = getView(R.id.fa_btn)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun isSwipeBackLayout(): Boolean {
        return true
    }

    companion object {

        fun startIntent() {
            UIUtils.startActivity(AboutActivity::class.java)
        }
    }
}
