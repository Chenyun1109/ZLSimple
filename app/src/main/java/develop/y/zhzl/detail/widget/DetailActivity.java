package develop.y.zhzl.detail.widget;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import develop.y.zhzl.R;
import develop.y.zhzl.detail.model.DetailModel;
import develop.y.zhzl.detail.presenter.DetailPresenterImpl;
import develop.y.zhzl.detail.view.DetailView;
import develop.y.zhzl.main.widget.DarkViewActivity;
import framework.utils.HtmlUtils;
import framework.utils.ImageLoaderUtils;
import framework.utils.StatusBarUtil;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class DetailActivity extends DarkViewActivity
        implements DetailView {

    private static final String BUNDLE_TYPE = "slug";
    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton floatingActionButton;
    private WebView webView;
    private ProgressBar progressBar;

    public static void startIntent(int slug) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_TYPE, slug);
        UIUtils.startActivity(DetailActivity.class, bundle);
    }


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageView(this, imageView);
        collapsingToolbar.setCollapsedTitleTextColor(UIUtils.getColor(R.color.white));
        collapsingToolbar.setExpandedTitleColor(UIUtils.getColor(R.color.purple));
        initWebView();
        DetailPresenterImpl presenter = new DetailPresenterImpl(this);
        presenter.netWorkRequest(getIntent().getExtras().getInt(BUNDLE_TYPE));
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(false);
    }


    @Override
    protected void initById() {
        collapsingToolbar = getView(R.id.collapsing_toolbar);
        imageView = getView(R.id.image);
        webView = getView(R.id.webview);
        floatingActionButton = getView(R.id.fa_btn);
        progressBar = getView(R.id.progressBar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected boolean isSwipeBackLayout() {
        return true;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void rxNetWorkError(Throwable e) {
        UIUtils.SnackBar(findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
    }

    @Override
    public void rxNetWorkSuccess(DetailModel mData) {
        webView.loadDataWithBaseURL(null, HtmlUtils.getHtml(mData.getContent()), HtmlUtils.getMimeType(), HtmlUtils.getCoding(), null);
        ImageLoaderUtils.display(imageView, mData.getTitleImage());
        collapsingToolbar.setTitle(mData.getTitle());
        floatingActionButton.setOnClickListener(view -> UIUtils.share(DetailActivity.this, getString(R.string.detail_share) + mData.getAuthor().getProfileUrl()));
    }

}
