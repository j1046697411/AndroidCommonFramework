package org.jzl.android.recyclerview.v3.core.vh;

import android.view.View;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderFactory;

public class DefaultViewHolderFactory  implements IViewHolderFactory<DefaultViewHolderFactory.DefaultViewHolder> {

    @NonNull
    @Override
    public DefaultViewHolder createViewHolder(@NonNull IOptions<?, DefaultViewHolder> options, @NonNull View itemView, int itemViewType) {
        return new DefaultViewHolder(options, itemView, itemViewType);
    }

    public static class DefaultViewHolder implements IViewHolder {

        private final IOptions<?, DefaultViewHolder> options;
        private final View itemView;
        private final int itemViewType;

        public DefaultViewHolder(IOptions<?, DefaultViewHolder> options, View itemView, int itemViewType) {
            this.options = options;
            this.itemView = itemView;
            this.itemViewType = itemViewType;
        }

        public IOptions<?, DefaultViewHolder> getOptions() {
            return options;
        }

        public View getItemView() {
            return itemView;
        }

        public int getItemViewType() {
            return itemViewType;
        }
    }
}
