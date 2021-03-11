package org.jzl.android.recyclerview.core.diff;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.configuration.Configuration;

import java.util.List;

public interface IDiffDispatcher<T, VH extends RecyclerView.ViewHolder> extends Component<T, VH> {

    @Override
    void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration);

    void submitList(List<T> newList);

    IDiffDispatcher<T, VH> setItemCallback(DiffUtil.ItemCallback<T> itemCallback);

}