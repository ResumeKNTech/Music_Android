package com.android_app.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android_app.music.ControllerApplication;
import com.android_app.music.R;
import com.android_app.music.ui.MainActivity;
import com.android_app.music.constant.GlobalFunction;
import com.android_app.music.databinding.FragmentFeedbackBinding;
import com.android_app.music.model.Feedback;
import com.android_app.music.utils.StringUtil;

public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding mFragmentFeedbackBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentFeedbackBinding = FragmentFeedbackBinding.inflate(inflater, container, false);

        mFragmentFeedbackBinding.tvSendFeedback.setOnClickListener(v -> onClickSendFeedback());

        return mFragmentFeedbackBinding.getRoot();
    }

    private void onClickSendFeedback() {
        if (getActivity() == null) {
            return;
        }
        MainActivity activity = (MainActivity) getActivity();

        String strName = mFragmentFeedbackBinding.edtName.getText().toString();
        String strPhone = mFragmentFeedbackBinding.edtPhone.getText().toString();
        String strEmail = mFragmentFeedbackBinding.edtEmail.getText().toString();
        String strComment = mFragmentFeedbackBinding.edtComment.getText().toString();

        if (StringUtil.isEmpty(strName)) {
            GlobalFunction.showToastMessage(activity, getString(R.string.name_require));
        } else if (StringUtil.isEmpty(strComment)) {
            GlobalFunction.showToastMessage(activity, getString(R.string.comment_require));
        } else {
            activity.showProgressDialog(true);
            Feedback feedback = new Feedback(strName, strPhone, strEmail, strComment);
            ControllerApplication.get(getActivity()).getFeedbackDatabaseReference()
                    .child(String.valueOf(System.currentTimeMillis()))
                    .setValue(feedback, (databaseError, databaseReference) -> {
                        activity.showProgressDialog(false);
                        sendFeedbackSuccess();
                    });
        }
    }

    public void sendFeedbackSuccess() {
        GlobalFunction.hideSoftKeyboard(getActivity());
        GlobalFunction.showToastMessage(getActivity(), getString(R.string.msg_send_feedback_success));
        mFragmentFeedbackBinding.edtName.setText("");
        mFragmentFeedbackBinding.edtPhone.setText("");
        mFragmentFeedbackBinding.edtEmail.setText("");
        mFragmentFeedbackBinding.edtComment.setText("");
    }
}
