package org.jzl.android.mvvm.v2;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IViewModelProvider {

    @Nullable
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getViewModel(String key, Class<VM1> viewModelType);

    @Nullable
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getViewModel(Class<VM1> viewModelType);

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 createViewModel(V1 view, String key, Class<VM1> viewModelType);

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 createViewModel(V1 view, Class<VM1> viewModelType);

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 bindVariableViewModel(@NonNull V1 view, int variableId, String viewModelKey, Class<VM1> viewModelType);

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 bindVariableViewModel(@NonNull V1 view, int variableId, Class<VM1> viewModelType);

    @Nullable
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getBindVariableViewModel(int variableId, String viewModelKey, Class<VM1> viewModelType);

    @Nullable
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getBindVariableViewModel(int variableId, Class<VM1> viewModelType);

}
