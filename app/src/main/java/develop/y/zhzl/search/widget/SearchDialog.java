package develop.y.zhzl.search.widget;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import develop.y.zhzl.R;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class SearchDialog {


    public static void startSearch(Activity activity, final SearchInterface searchInterface) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View editView = UIUtils.getInflate(R.layout.search_layout);
        final EditText suffixEditText = (EditText) editView.findViewById(R.id.suffix);
        final EditText limitEditText = (EditText) editView.findViewById(R.id.limit);

        builder.setTitle(UIUtils.getString(R.string.search_title));
        builder.setView(editView);
        builder.setPositiveButton(UIUtils.getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchInterface.startSearch(suffixEditText.getText().toString().trim(), limitEditText.getText().toString().trim());
                UIUtils.offKeyboard();
            }
        });
        builder.setNegativeButton(UIUtils.getString(R.string.dialog_finish),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UIUtils.offKeyboard();
                    }
                });
        builder.create().show();
    }


    public interface SearchInterface {
        void startSearch(String suffix, String limit);
    }

}
