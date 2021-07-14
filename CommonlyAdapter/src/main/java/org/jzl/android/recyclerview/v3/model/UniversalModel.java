package org.jzl.android.recyclerview.v3.model;

import org.jzl.android.recyclerview.v3.core.layout.SpanSize;
import org.jzl.lang.util.ObjectUtils;

public class UniversalModel implements IClassifiable, IDataOwner, Identifiable, IExtractable, IModel, ISelectable, ISpanSizable {

    private final long id;
    private final int itemViewType;
    private final IExtractable extras;
    private Object data;
    private boolean checked;
    private final ISpanSizable spanSize;

    public UniversalModel(long id, int itemViewType, IExtractable extras, Object data, boolean checked, ISpanSizable spanSize) {
        this.id = id;
        this.itemViewType = itemViewType;
        this.extras = extras;
        this.data = data;
        this.checked = checked;
        this.spanSize = spanSize;
    }
    @Override
    public long getId() {
        if (data instanceof Identifiable) {
            return ((Identifiable) data).getId();
        }
        return id;
    }

    @Override
    public int getItemType() {
        if (data instanceof IClassifiable) {
            return ((IClassifiable) data).getItemType();
        }
        return itemViewType;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public <E> E getExtra(int key) {
        return extras.getExtra(key);
    }

    @Override
    public <E> E getExtra(int key, E defValue) {
        return extras.getExtra(key, defValue);
    }

    @Override
    public void putExtra(int key, Object value) {
        extras.putExtra(key, value);
    }

    @Override
    public boolean hasExtra(int key) {
        return extras.hasExtra(key);
    }

    @Override
    public void removeExtra(int key) {
        extras.removeExtra(key);
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void checked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int getSpanSize(int spanCount) {
        return spanSize.getSpanSize(spanCount);
    }

    public static Builder build(Object data){
        return new Builder(data);
    }

    public static class Builder {
        private long id;
        private int itemViewType;
        private IExtractable extras = new ExtraModel();
        private Object data;
        private boolean checked;
        private ISpanSizable spanSize = SpanSize.ONE;

        private Builder(Object data) {
            this.data = data;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setItemViewType(int itemViewType){
            this.itemViewType = itemViewType;
            return this;
        }

        public Builder setChecked(boolean checked) {
            this.checked = checked;
            return this;
        }

        public Builder setSpanSize(ISpanSizable spanSize) {
            this.spanSize = ObjectUtils.get(spanSize, this.spanSize);
            return this;
        }

        public Builder setExtras(IExtractable extras) {
            this.extras = ObjectUtils.get(extras, this.extras);
            return this;
        }

        public Builder setData(Object data) {
            this.data = data;
            return this;
        }

        public UniversalModel build(){
            return new UniversalModel(this.id, this.itemViewType, this.extras, this.data, this.checked, this.spanSize);
        }
    }

    @Override
    public String toString() {
        return "UniversalModel{" +
                "id=" + id +
                ", itemViewType=" + itemViewType +
                ", extras=" + extras +
                ", data=" + data +
                ", checked=" + checked +
                ", spanSize=" + spanSize +
                '}';
    }
}
