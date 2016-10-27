package develop.y.zhzl.list.widget;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import develop.y.zhzl.R;
import framework.base.BaseDialog;
import framework.utils.UIUtils;

import static framework.utils.UIUtils.getString;

/**
 * by y on 2016/8/7.
 */
public class ListDialog extends BaseDialog implements View.OnClickListener {

    private TextView textView;
    private String suffix;

    public static void start(Context context, String suffix) {
        new ListDialog(context).initData(suffix);
    }

    protected ListDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreateView() {
        view = View.inflate(getContext(), R.layout.list_dialog_layout, null);
    }

    @Override
    protected void initById() {
        textView = (TextView) view.findViewById(R.id.tv_title);
        view.findViewById(R.id.btn_list).setOnClickListener(this);
    }

    private void initData(String suffix) {
        this.suffix = suffix;
        textView.setText(getContext().getString(R.string.suffix_title) + this.suffix + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list:
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(suffix);
                UIUtils.Toast(getString(R.string.suffix_copy));
                dismiss();
                break;
        }
    }
}
