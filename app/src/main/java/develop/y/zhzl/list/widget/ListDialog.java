package develop.y.zhzl.list.widget;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import develop.y.zhzl.R;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class ListDialog {


    public static void copySuffix(final Activity activity, final String suffix) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(UIUtils.getString(R.string.suffix_title) + ":" + suffix);
        builder.setMessage(UIUtils.getString(R.string.suffix_message));
        builder.setPositiveButton(UIUtils.getString(R.string.list_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                //noinspection deprecation
                cm.setText(suffix);
                UIUtils.SnackBar(activity.findViewById(R.id.coordinatorLayout), UIUtils.getString(R.string.suffix_copy));
            }
        });
        builder.setNegativeButton(UIUtils.getString(R.string.dialog_finish),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }
}
