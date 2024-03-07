package com.android_app.music.ui;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android_app.music.R;

public abstract class BaseActivity extends AppCompatActivity {
    protected MaterialDialog progressDialog, alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createProgressDialog();
        createAlertDialog();
    }

    // Phương thức để tạo dialog tiến trình
    public void createProgressDialog() {
        progressDialog = new MaterialDialog.Builder(this)
                .content(R.string.msg_please_waiting)
                .progress(true, 0)
                .build();
    }

    // Phương thức để hiển thị hoặc ẩn dialog tiến trình
    public void showProgressDialog(boolean value) {
        if (value) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    // Phương thức để ẩn dialog tiến trình
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    // Phương thức để tạo dialog cảnh báo
    public void createAlertDialog() {
        alertDialog = new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .positiveText(R.string.action_ok)
                .cancelable(false)
                .build();
    }

    // Phương thức để hiển thị dialog cảnh báo với một thông báo cụ thể
    public void showAlertDialog(String errorMessage) {
        alertDialog.setContent(errorMessage);
        alertDialog.show();
    }

    // Phương thức để hiển thị dialog cảnh báo với một nguồn tài nguyên chuỗi cụ thể
    public void showAlertDialog(@StringRes int resourceId) {
        alertDialog.setContent(resourceId);
        alertDialog.show();
    }

    // Phương thức để thiết lập khả năng hủy bỏ cho dialog tiến trình
    public void setCancelProgress(boolean isCancel) {
        if (progressDialog != null) {
            progressDialog.setCancelable(isCancel);
        }
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        super.onDestroy();
    }
}
