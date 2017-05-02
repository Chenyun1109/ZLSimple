package develop.y.zhzl.detail.widget


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar

import develop.y.zhzl.R
import develop.y.zhzl.detail.model.DetailModel
import develop.y.zhzl.detail.presenter.DetailPresenterImpl
import develop.y.zhzl.detail.view.DetailView
import develop.y.zhzl.main.widget.DarkViewActivity
import framework.utils.HtmlUtils
import framework.utils.ImageLoaderUtils
import framework.utils.StatusBarUtil
import framework.utils.UIUtils

/**
 * by y on 2016/8/7.
 */
class DetailActivity : DarkViewActivity(), DetailView {
    private var imageView: ImageView? = null
    private var collapsingToolbar: CollapsingToolbarLayout? = null
    private var floatingActionButton: FloatingActionButton? = null
    private var webView: WebView? = null
    private var progressBar: ProgressBar? = null


    override fun initCreate() {
        StatusBarUtil.setTranslucentForImageView(this, imageView!!)
        collapsingToolbar!!.setCollapsedTitleTextColor(UIUtils.getColor(R.color.white))
        collapsingToolbar!!.setExpandedTitleColor(UIUtils.getColor(R.color.purple))
        initWebView()
        val presenter = DetailPresenterImpl(this)
        presenter.netWorkRequest(intent.extras.getInt(BUNDLE_TYPE))
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val settings = webView!!.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = false
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.domStorageEnabled = true
        settings.setAppCacheEnabled(false)
    }


    override fun initById() {
        collapsingToolbar = getView(R.id.collapsing_toolbar)
        imageView = getView(R.id.image)
        webView = getView(R.id.webview)
        floatingActionButton = getView(R.id.fa_btn)
        progressBar = getView(R.id.progressBar)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun isSwipeBackLayout(): Boolean {
        return true
    }

    override fun showProgress() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar!!.visibility = View.GONE
    }

    override fun rxNetWorkError(e: Throwable) {
        UIUtils.snackBar(getView(R.id.coordinatorLayout), getString(R.string.network_error))
    }

    override fun rxNetWorkSuccess(mData: DetailModel) {
        webView!!.loadDataWithBaseURL(null, HtmlUtils.getHtml(mData.content), HtmlUtils.getMimeType(), HtmlUtils.getCoding(), null)
        ImageLoaderUtils.display(imageView, mData.titleImage!!)
        collapsingToolbar!!.title = mData.title
        floatingActionButton!!.setOnClickListener { UIUtils.share(this@DetailActivity, getString(R.string.detail_share) + mData.author!!.profileUrl!!) }
    }

    companion object {

        private val BUNDLE_TYPE = "slug"

        fun startIntent(slug: Int) {
            val bundle = Bundle()
            bundle.putInt(BUNDLE_TYPE, slug)
            UIUtils.startActivity(DetailActivity::class.java, bundle)
        }
    }

}
