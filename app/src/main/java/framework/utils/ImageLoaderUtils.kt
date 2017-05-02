package framework.utils


import android.widget.ImageView

import com.bumptech.glide.Glide

import develop.y.zhzl.R

/**
 * by y on 2016/8/7.
 */
object ImageLoaderUtils {

    fun display(imageView: ImageView?, url: String) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(imageView.context).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.error).centerCrop().into(imageView)
    }

}
