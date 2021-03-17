package org.jzl.android.mvvm.core;

import android.app.Application;

import org.jzl.android.mvvm.vm.AndroidViewModel;

import java.lang.reflect.InvocationTargetException;

public class ViewModelProviders {

    public static final IViewModelFactory DEFAULT_VIEW_MODEL_FACTORY = new SimpleViewModelFactory();

    public static ViewModelProvider of(Application application, IViewModelStoreOwner viewModelStoreOwner) {
        return of(viewModelStoreOwner, new AndroidViewModelViewModelFactory(application));
    }

    public static ViewModelProvider of(IViewModelStoreOwner viewModelStoreOwner, IViewModelFactory viewModelFactory) {
        return new ViewModelProvider(viewModelStoreOwner.getTargetViewModelStore(), viewModelFactory);
    }

    static class AndroidViewModelViewModelFactory extends SimpleViewModelFactory implements IKeyViewModelFactory {

        private final Application application;

        public AndroidViewModelViewModelFactory(Application application) {
            this.application = application;
        }

        @Override
        public <VM extends IViewModel> VM create(String key, Class<VM> viewModelType) {
            if (AndroidViewModel.class.isAssignableFrom(viewModelType)) {
                try {
                    return viewModelType.getConstructor(Application.class, String.class).newInstance(application, key);
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    return create(viewModelType);
                }
            }
            return create(viewModelType);
        }

    }

    static class SimpleViewModelFactory implements IViewModelFactory {

        @Override
        public <VM extends IViewModel> VM create(Class<VM> viewModelType) {
            try {
                return viewModelType.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
