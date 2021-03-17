package org.jzl.android.app;

import android.app.Application;

import org.jzl.android.mvvm.M;
import org.jzl.android.recyclerview.util.Logger;
import org.jzl.router.core.JRouter;

public class MApplication extends Application {

    private static final Logger log = Logger.logger(MApplication.class);

    @Override
    public void onCreate() {
        super.onCreate();
        M.initialise(this);
        JRouter.getInstance().init(this);
    }
}
