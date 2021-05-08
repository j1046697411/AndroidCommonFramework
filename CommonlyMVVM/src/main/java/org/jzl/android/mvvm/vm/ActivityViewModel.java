package org.jzl.android.mvvm.vm;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import org.jzl.android.mvvm.core.IView;
import org.jzl.android.mvvm.model.ActivityFinishModel;

public class ActivityViewModel extends AbstractViewModel {

    public final MutableLiveData<ActivityFinishModel> finish = new MutableLiveData<>();

    public void finish() {
        finishActivity(Activity.RESULT_CANCELED);
    }

    public void finishActivity(int resultCode) {
        finishActivity(resultCode, null);
    }

    public void finishActivity(int resultCode, Intent resultData) {
        finish.postValue(new ActivityFinishModel(resultCode, resultData));
    }

}
