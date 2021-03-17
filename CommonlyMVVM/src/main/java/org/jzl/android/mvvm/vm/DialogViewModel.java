package org.jzl.android.mvvm.vm;

import androidx.lifecycle.MutableLiveData;

public class DialogViewModel extends AbstractViewModel {

    public final MutableLiveData<Boolean> dismiss = new MutableLiveData<>();

    public void dismiss() {
        this.dismiss.setValue(true);
    }
}
