package org.jzl.android.mvvm.model;

import android.content.Intent;

import java.io.Serializable;

public final class ActivityFinishModel implements Serializable {

    private final int resultCode;
    private final Intent resultData;

    public ActivityFinishModel(int resultCode, Intent resultData) {
        this.resultCode = resultCode;
        this.resultData = resultData;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getResultData() {
        return resultData;
    }
}
