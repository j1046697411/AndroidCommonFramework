package org.jzl.lang.util;

import org.jzl.lang.fun.BinaryConsumer;
import org.jzl.lang.fun.BinaryPredicate;
import org.jzl.lang.fun.Consumer;
import org.jzl.lang.fun.IntBinaryConsumer;
import org.jzl.lang.fun.IntBinaryPredicate;
import org.jzl.lang.fun.IntConsumer;
import org.jzl.lang.fun.IntPredicate;
import org.jzl.lang.fun.Predicate;
import org.jzl.lang.fun.Supplier;
import org.jzl.lang.util.holder.BinaryHolder;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ForeachUtils {

    private ForeachUtils() {
    }

    /**
     * 遍历整形数组，并在过程中传递出下标
     *
     * @param array    被遍历的数组
     * @param consumer 含下标的消费者
     */
    public static void each(int[] array, IntBinaryConsumer consumer) {
        ObjectUtils.requireNonNull(consumer, "consumer");
        if (ArrayUtils.nonEmpty(array)) {
            int length = array.length;
            for (int i = 0; i < length; i++) {
                consumer.accept(i, array[i]);
            }
        }
    }

    /**
     * 遍历整形数组，在过程中满足条件就跳出遍历
     *
     * @param array     被遍历的数组
     * @param predicate 包含下标的条件
     */
    public static void eachIfBack(int[] array, IntBinaryPredicate predicate) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (ArrayUtils.nonEmpty(array)) {
            int length = array.length;
            for (int i = 0; i < length; i++) {
                if (predicate.test(i, array[i])) {
                    return;
                }
            }
        }
    }

    /**
     * 遍历数组，并在过程中传递出下标
     *
     * @param array    被遍历的数组
     * @param consumer 含下标的消费者
     */
    public static <T> void each(T[] array, IntConsumer<T> consumer) {
        ObjectUtils.requireNonNull(consumer, "consumer");

        if (ArrayUtils.nonEmpty(array)) {
            int length = array.length;
            for (int i = 0; i < length; i++) {
                consumer.accept(i, array[i]);
            }
        }
    }

    /**
     * 遍历数组，不包含下标
     *
     * @param array    被遍历的数组
     * @param consumer 消费者
     */
    public static <T> void each(T[] array, Consumer<T> consumer) {
        ObjectUtils.requireNonNull(consumer, "consumer");

        if (ArrayUtils.nonEmpty(array)) {
            for (T a : array) {
                consumer.accept(a);
            }
        }
    }

    /**
     * 遍历数组，在过程中满足条件就跳出遍历
     *
     * @param array     被遍历的数组
     * @param predicate 包含下标的条件
     */
    public static <T> void eachIfBack(T[] array, IntPredicate<T> predicate) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (ArrayUtils.nonEmpty(array)) {
            int length = array.length;
            for (int i = 0; i < length; i++) {
                if (predicate.test(i, array[i])) {
                    return;
                }
            }
        }
    }

    /**
     * 遍历map，在过程中满足条件就跳出遍历
     *
     * @param map       被遍历的map
     * @param predicate 条件
     */
    public static <K, V> void eachIfBack(Map<K, V> map, BinaryPredicate<K, V> predicate) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (MapUtils.nonEmpty(map)) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (predicate.test(entry.getKey(), entry.getValue())) {
                    return;
                }
            }
        }
    }

    /**
     * 遍历map
     *
     * @param map      被遍历的map
     * @param consumer 条件
     */
    public static <K, V> void each(Map<K, V> map, BinaryConsumer<K, V> consumer) {
        ObjectUtils.requireNonNull(consumer, "consumer");
        if (MapUtils.nonEmpty(map)) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                consumer.apply(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 遍历list，包含下标
     *
     * @param list     被遍历的list
     * @param consumer 包含下标的消费者
     */
    public static <T> void each(List<T> list, IntConsumer<T> consumer) {
        ObjectUtils.requireNonNull(consumer, "consumer");
        if (CollectionUtils.nonEmpty(list)) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                consumer.accept(i, list.get(i));
            }
        }
    }

    /**
     * 遍历list，满足添加跳出遍历
     *
     * @param list      被遍历的list
     * @param predicate 带下下标的条件
     * @param <T>       泛型对象
     */
    public static <T> void eachIfBack(List<T> list, IntPredicate<T> predicate) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (CollectionUtils.nonEmpty(list)) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (predicate.test(i, list.get(i))) {
                    return;
                }
            }
        }
    }

    /**
     * 遍历iterable
     *
     * @param iterable 被遍历的iterable
     * @param consumer 带下下标的消费者
     * @param <T>      泛型对象
     */
    public static <T> void each(Iterable<T> iterable, Consumer<T> consumer) {
        ObjectUtils.requireNonNull(consumer, "consumer");
        if (ObjectUtils.nonNull(iterable)) {
            for (T t : iterable) {
                consumer.accept(t);
            }
        }
    }

    /**
     * 遍历iterable，满足条件跳出遍历
     *
     * @param iterable  被遍历的iterable
     * @param predicate 条件
     * @param <T>       泛型对象
     */
    public static <T> void eachIfBack(Iterable<T> iterable, Predicate<T> predicate) {
        ObjectUtils.requireNonNull(predicate, "consumer");
        if (ObjectUtils.nonNull(iterable)) {
            for (T t : iterable) {
                if (predicate.test(t)) {
                    return;
                }
            }
        }
    }

    public static <T> void eachIfRemove(Iterable<T> iterable, BinaryConsumer<T, Remover> consumer) {
        if (ObjectUtils.nonNull(iterable)) {
            eachIfRemove(iterable.iterator(), consumer);
        }
    }

    public static <T> void eachIfRemove(Iterator<T> iterator, BinaryConsumer<T, Remover> consumer) {
        ObjectUtils.requireNonNull(consumer, "consumer");
        if (ObjectUtils.nonNull(iterator)) {
            Remover remover = iterator::remove;
            while (iterator.hasNext()) {
                consumer.apply(iterator.next(), remover);
            }
        }
    }

    public static <T> T removeByOne(Iterable<T> iterable, Predicate<T> predicate) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (ObjectUtils.nonNull(iterable)) {
            return removeByOne(iterable.iterator(), predicate);
        }
        return null;
    }

    public static <T> T removeByOne(Iterator<T> iterator, Predicate<T> predicate) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (ObjectUtils.nonNull(iterator)) {
            while (iterator.hasNext()) {
                T next = iterator.next();
                if (predicate.test(next)) {
                    iterator.remove();
                    return next;
                }
            }
        }
        return null;
    }

    public static <K, V> BinaryHolder<K, V> removeByOne(Map<K, V> map, BinaryPredicate<K, V> predicate) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (MapUtils.nonEmpty(map)) {
            Map.Entry<K, V> retEntry = removeByOne(map.entrySet(), entry -> predicate.test(entry.getKey(), entry.getValue()));
            if (ObjectUtils.nonNull(retEntry)) {
                return BinaryHolder.of(retEntry.getKey(), retEntry.getValue());
            }
        }
        return null;
    }

    public static <T> void remove(Iterator<T> iterator, Predicate<T> predicate) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (ObjectUtils.nonNull(iterator)) {
            while (iterator.hasNext()) {
                if (predicate.test(iterator.next())) {
                    iterator.remove();
                }
            }
        }
    }

    public static <T> void remove(Iterable<T> iterable, Predicate<T> predicate) {
        if (ObjectUtils.nonNull(iterable)) {
            remove(iterable.iterator(), predicate);
        }
    }

    public static <K, V> void remove(Map<K, V> map, BinaryPredicate<K, V> predicate) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (MapUtils.nonEmpty(map)) {
            remove(map.entrySet(), target -> predicate.test(target.getKey(), target.getValue()));
        }
    }

    public static <T> T findByOne(Iterator<T> iterator, Predicate<T> predicate) {
        return findByOne(iterator, predicate, (Supplier<T>) null);
    }

    public static <T> T findByOne(Iterable<T> iterable, Predicate<T> predicate) {
        return findByOne(iterable, predicate, null);
    }

    public static <T> T findByOne(Iterator<T> iterator, Predicate<T> predicate, T def) {
        return findByOne(iterator, predicate, (Supplier<T>) () -> def);
    }

    public static <T> T findByOne(Iterator<T> iterator, Predicate<T> predicate, Supplier<T> defSupplier) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (ObjectUtils.nonNull(iterator)) {
            while (iterator.hasNext()) {
                T next = iterator.next();
                if (predicate.test(next)) {
                    return next;
                }
            }
        }
        return ObjectUtils.nonNull(defSupplier) ? defSupplier.get() : null;
    }

    public static <T> T findByOne(Iterable<T> iterable, Predicate<T> predicate, Supplier<T> defSupplier) {
        if (ObjectUtils.nonNull(iterable)) {
            return findByOne(iterable.iterator(), predicate, defSupplier);
        } else {
            return ObjectUtils.nonNull(defSupplier) ? defSupplier.get() : null;
        }
    }

    public static <K, V> V findByOneValue(Map<K, V> map, BinaryPredicate<K, V> predicate) {
        return findByOneValue(map, predicate, (Supplier<V>) null);
    }

    public static <K, V> V findByOneValue(Map<K, V> map, BinaryPredicate<K, V> predicate, V def) {
        return findByOneValue(map, predicate, (Supplier<V>) () -> def);
    }

    public static <K, V> V findByOneValue(Map<K, V> map, BinaryPredicate<K, V> predicate, Supplier<V> defSupplier) {
        if (MapUtils.nonEmpty(map)) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (predicate.test(entry.getKey(), entry.getValue())) {
                    return entry.getValue();
                }
            }
        }
        return ObjectUtils.nonNull(defSupplier) ? defSupplier.get() : null;
    }

    public static <K, V> BinaryHolder<K, V> findByOne(Map<K, V> map, BinaryPredicate<K, V> predicate) {
        return findByOne(map, predicate, null);
    }

    public static <K, V> BinaryHolder<K, V> findByOne(Map<K, V> map, BinaryPredicate<K, V> predicate, K key, V value) {
        return findByOne(map, predicate, () -> BinaryHolder.of(key, value));
    }

    public static <K, V> BinaryHolder<K, V> findByOne(Map<K, V> map, BinaryPredicate<K, V> predicate, Supplier<BinaryHolder<K, V>> defSupplier) {
        ObjectUtils.requireNonNull(predicate, "predicate");
        if (MapUtils.nonEmpty(map)) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (predicate.test(entry.getKey(), entry.getValue())) {
                    return BinaryHolder.of(entry.getKey(), entry.getValue());
                }
            }
        }
        return ObjectUtils.nonNull(defSupplier) ? defSupplier.get() : null;
    }

    public interface Remover {
        void remove();
    }

}
