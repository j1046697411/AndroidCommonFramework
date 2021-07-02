package org.jzl.android.recyclerview.core.data;

import androidx.annotation.NonNull;

import org.jzl.lang.util.ObjectUtils;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

public class CommonlyModel implements Identifiable, Classifiable, Selectable, SpanSizable, Extractable, Differentiable<CommonlyModel> {

    private static final AtomicLong ID = new AtomicLong(0);

    private final long incrementId;
    private final Extractable ext;
    private final Object data;
    private boolean checked;
    private SpanSizable spanSize;
    private int type;

    CommonlyModel(Object data, int type, SpanSizable spanSize) {
        ext = new SimpleExtraEntity();
        this.data = data;
        this.type = type;
        this.spanSize = spanSize;
        this.incrementId = ID.incrementAndGet();
    }

    public static CommonlyModel of(Object data, int type) {
        return of(data, type, SpanSize.ONE);
    }

    public static CommonlyModel of(Object data, int type, SpanSizable spanSize) {
        return new CommonlyModel(data, type, spanSize);
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

    @SuppressWarnings("all")
    public <T> T getData() {
        return (T) data;
    }

    @Override
    public <E> E getExtra(int key) {
        return ext.getExtra(key);
    }

    @Override
    public <E> E getExtra(int key, E defValue) {
        return ext.getExtra(key, defValue);
    }

    @Override
    public void putExtra(int key, Object value) {
        ext.putExtra(key, value);
    }

    @Override
    public boolean hasExtra(int key) {
        return ext.hasExtra(key);
    }

    @Override
    public void removeExtra(int key) {
        ext.removeExtra(key);
    }

    private boolean canCompare(CommonlyModel newItem, CommonlyModel oldItem) {
        Object newData = newItem.getData();
        Object oldData = oldItem.getData();
        if (ObjectUtils.isNull(newData) || ObjectUtils.isNull(oldData)) {
            return false;
        }
        return newData.getClass().equals(oldData.getClass()) && newData instanceof Differentiable && oldData instanceof Differentiable;
    }

    @Override
    public boolean areItemsTheSame(CommonlyModel data) {
        if (canCompare(this, data)) {
            return ((Differentiable<?>) this.data).areItemsTheSame(data.getData());
        }
        return data.getId() == getId();
    }

    @Override
    public boolean areContentsTheSame(CommonlyModel data) {
        if (equals(data)) {
            return true;
        }
        if (canCompare(this, data)) {
            return ((Differentiable<?>) this.data).areItemsTheSame(data.getData());
        }
        return false;
    }

    @Override
    public Object getChangePayload(CommonlyModel data) {
        if (canCompare(this, data)) {
            return ((Differentiable<?>) this.data).getChangePayload(data.getData());
        }
        return Collections.emptyList();
    }

    @Override
    public long getId() {
        if (data instanceof Identifiable) {
            return ((Identifiable) data).getId();
        }
        return incrementId;
    }

    @Override
    public int getType() {
        if (data instanceof Classifiable) {
            return ((Classifiable) data).getType();
        }
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSpanSize(SpanSizable spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    @NonNull
    public String toString() {
        return "CommonlyModel{" +
                "incrementId=" + incrementId +
                ", checked=" + checked +
                ", ext=" + ext +
                ", data=" + data +
                ", spanSize=" + spanSize +
                ", type=" + type +
                '}';
    }
}
