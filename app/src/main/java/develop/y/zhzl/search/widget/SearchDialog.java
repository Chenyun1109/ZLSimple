package develop.y.zhzl.search.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import develop.y.zhzl.R;
import framework.base.BaseDialog;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class SearchDialog extends BaseDialog implements View.OnClickListener {

    private EditText suffixEditText;
    private EditText limitEditText;
    private SearchInterface searchInterface = null;

    public static void start(Context context, SearchInterface searchInterface) {
        new SearchDialog(context, searchInterface);
    }

    protected SearchDialog(@NonNull Context context, SearchInterface searchInterface) {
        super(context);
        this.searchInterface = searchInterface;
    }

    @Override
    protected void onCreateView() {
        view = View.inflate(getContext(), R.layout.search_layout, null);
    }

    @Override
    protected void initById() {
        view.findViewById(R.id.btn_search).setOnClickListener(this);
        view.findViewById(R.id.btn_annal).setOnClickListener(this);
        suffixEditText = (EditText) view.findViewById(R.id.suffix);
        limitEditText = (EditText) view.findViewById(R.id.limit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                if (searchInterface != null) {
                    searchInterface.startSearch(suffixEditText.getText().toString().trim(), limitEditText.getText().toString().trim());
                    UIUtils.offKeyboard();
                    dismiss();
                }
                break;
            case R.id.btn_annal:
                AnnalActivity.startIntent();
                dismiss();
                break;
        }
    }


    public interface SearchInterface {
        void startSearch(String suffix, String limit);
    }

}
