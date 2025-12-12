import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.BiConsumer;
import java.util.function.Supplier;


public class GenericUtils {

    public static <T, P> List<P> map(List<T> list, Function<T, P> f) {
        List<P> result = new ArrayList<>();
        for (T item : list) {
            result.add(f.apply(item));
        }
        return result;
    }
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
    // Интерфейс для объединения двух элементов
    @FunctionalInterface
    public interface Reducer<T> {
        T apply(T a, T b);
    }

    // Метод reduce
    public static <T> T reduce(List<T> list, Reducer<T> reducer, T defaultValue) {
        if (list == null || list.isEmpty()) return defaultValue;

        T result = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            result = reducer.apply(result, list.get(i));
        }
        return result;
    }

    public static <T, P> P collect(List<T> list, Supplier<P> supplier, BiConsumer<P, T> collector) {
        P container = supplier.get();
        for (T item : list) {
            collector.accept(container, item);
        }
        return container;
    }
}
