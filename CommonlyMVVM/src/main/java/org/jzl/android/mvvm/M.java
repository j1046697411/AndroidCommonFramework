package org.jzl.android.mvvm;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class M {
    public static void initialise(Application application) {
        ARouter.init(application);
    }
}
