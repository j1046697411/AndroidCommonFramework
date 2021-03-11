package org.jzl.android.app.vm;

import androidx.databinding.ObservableField;

import org.jzl.android.mvvm.vm.AbstractViewModel;

public class MainViewModel extends AbstractViewModel {

    public final ObservableField<String> helloWorld = new ObservableField<>("Hello World!");

}
