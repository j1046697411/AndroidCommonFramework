package org.jzl.android.recyclerview.util.datablock.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jzl.android.recyclerview.util.datablock.DataSource;
import org.jzl.lang.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class AbstractDataSource<T> implements DataSource<T> {

    protected abstract List<T> proxy();

    @Override
    public int size() {
        return proxy().size();
    }

    @Override
    public boolean isEmpty() {
        return proxy().isEmpty();
    }

    @Override
    public void move(int fromPosition, int toPosition) {
        CollectionUtils.move(proxy(), fromPosition, toPosition);
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return proxy().contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return proxy().iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return proxy().toArray();
    }

    @NonNull
    @Override
    @SuppressWarnings("all")
    public <T1> T1[] toArray(@NonNull T1[] a) {
        return proxy().toArray(a);
    }

    @Override
    public boolean add(T t) {
        return proxy().add(t);
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return proxy().remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return proxy().containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        return proxy().addAll(c);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> c) {
        return proxy().addAll(index, c);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return proxy().removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return proxy().retainAll(c);
    }

    @Override
    public void clear() {
        proxy().clear();
    }

    @Override
    public T get(int index) {
        return proxy().get(index);
    }

    @Override
    public T set(int index, T element) {
        return proxy().set(index, element);
    }

    @Override
    public void add(int index, T element) {
        proxy().add(index, element);
    }

    @Override
    public T remove(int index) {
        return proxy().remove(index);
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return proxy().indexOf(o);
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return proxy().lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator() {
        return proxy().listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return proxy().listIterator(index);
    }

    @NonNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return proxy().subList(fromIndex, toIndex);
    }

    @NonNull
    @Override
    public final String toString() {
        return String.valueOf(proxy());
    }

}
