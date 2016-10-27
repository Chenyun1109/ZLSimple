package develop.y.zhzl.main;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import develop.y.zhzl.R;
import framework.utils.StatusBarUtil;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class AboutActivity extends DarkViewActivity {

    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton floatingActionButton;

    public static void startIntent() {
        UIUtils.startActivity(AboutActivity.class);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        collapsingToolbar.setTitle(getString(R.string.about));
        collapsingToolbar.setCollapsedTitleTextColor(UIUtils.getColor(R.color.white));
        collapsingToolbar.setExpandedTitleColor(UIUtils.getColor(R.color.purple));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.share(UIUtils.getString(R.string.share_about));
            }
        });
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setTranslucentForImageView(this, imageView);
    }

    @Override
    protected void initById() {
        collapsingToolbar = getView(R.id.collapsing_toolbar);
        imageView = getView(R.id.image);
        floatingActionButton = getView(R.id.fa_btn);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected boolean isSwipeBackLayout() {
        return true;
    }
}
