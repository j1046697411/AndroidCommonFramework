package org.jzl.android.mvvm.view;

import androidx.annotation.NonNull;

public interface IDialogFragment extends IDialogView, IFragmentView {

    void show(@NonNull IActivityView activityView, String tag);

    void showNow(@NonNull IActivityView activityView, String tag);

}
