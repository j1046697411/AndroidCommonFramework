package org.jzl.android.app.vm;

import androidx.databinding.ObservableField;

import org.jzl.android.app.MainActivity;
import org.jzl.android.app.MainActivityView;
import org.jzl.android.mvvm.vm.AbstractViewModel;

public class MainViewModel extends AbstractViewModel<MainActivityView> {

    public final ObservableField<String> helloWorld = new ObservableField<>("Hello World!");

    @Override
    public void initialise() {
        super.initialise();
        MainActivity.log.i("initialise");
    }

    @Override
    public void unbind() {
        super.unbind();
        MainActivity.log.i("unbind");
    }

    public void showDialog() {
        view.showDialog();
    }

    public void finish(){
        view.finish();
    }
}
