package develop.y.zhzl.list.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import develop.y.zhzl.R;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class ListDialog {


    @SuppressLint("SetTextI18n")
    public static void copySuffix(final Activity activity, final String suffix) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View listView = UIUtils.getInflate(R.layout.list_dialog_layout);
        TextView textView = (TextView) listView.findViewById(R.id.tv_title);
        Button button = (Button) listView.findViewById(R.id.btn_list);
        textView.setText(UIUtils.getString(R.string.suffix_title) + ":" + suffix);
        builder.setView(listView);
        final Dialog dialog = builder.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(suffix);
                UIUtils.SnackBar(activity.findViewById(R.id.coordinatorLayout), UIUtils.getString(R.string.suffix_copy));
                dialog.dismiss();
            }
        });
    }
}
