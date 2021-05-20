package org.jzl.android.app.vm;

import org.jzl.android.app.MainActivity;
import org.jzl.android.app.databinding.DialogTestBinding;
import org.jzl.android.mvvm.IViewModel;
import org.jzl.android.mvvm.view.helper.DialogFragmentHelper;
import org.jzl.android.mvvm.vm.AbstractViewModel;

public class TestDialogViewModel extends AbstractViewModel<DialogFragmentHelper<TestDialogViewModel, DialogTestBinding>> implements IViewModel<DialogFragmentHelper<TestDialogViewModel, DialogTestBinding>> {

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
}
