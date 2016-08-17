package develop.y.zhzl.search.widget;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import develop.y.zhzl.R;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class SearchDialog {


    public static void startSearch(final Activity activity, final SearchInterface searchInterface) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View editView = UIUtils.getInflate(R.layout.search_layout);
        final EditText suffixEditText = (EditText) editView.findViewById(R.id.suffix);
        final EditText limitEditText = (EditText) editView.findViewById(R.id.limit);
        builder.setView(editView);
        final Dialog dialog = builder.show();
        editView.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInterface.startSearch(suffixEditText.getText().toString().trim(), limitEditText.getText().toString().trim());
                UIUtils.offKeyboard();
                dialog.dismiss();
            }
        });
        editView.findViewById(R.id.btn_annal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnnalActivity.startIntent();
                dialog.dismiss();
            }
        });
    }


    public interface SearchInterface {
        void startSearch(String suffix, String limit);
    }

}
