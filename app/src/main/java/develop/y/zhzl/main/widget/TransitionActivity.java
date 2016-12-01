package develop.y.zhzl.main.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import develop.y.zhzl.R;
import framework.App;
import framework.data.Constant;
import framework.utils.CacheUitls;
import framework.utils.RxBus;
import framework.utils.UIUtils;

public class TransitionActivity extends Activity {

    public static void startIntent() {
        UIUtils.startActivity(TransitionActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        final ImageView imageView = (ImageView) findViewById(R.id.imageview);

        Bitmap bitmap = CacheUitls.getInstance().get(Constant.BITMAP_CACHE_KEY);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            startAnimation();
        }
    }


    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(1f).setDuration(400);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                App.getInstance().refreshAllActivity();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                RxBus.getInstance().send(Constant.THEME_TAG, "");
                finish();
                overridePendingTransition(0, android.R.anim.fade_out);
                CacheUitls.getInstance().remove(Constant.BITMAP_CACHE_KEY);
            }
        });
        animator.start();
    }

}
