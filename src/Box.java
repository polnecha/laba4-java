public class Box<T> {

    private T item;

    public boolean isEmpty() {
        return item == null;
    }

    public void put(T obj) {
        if (item != null) {
            throw new IllegalStateException("Коробка уже заполнена!");
        }
        item = obj;
    }

    public T take() {
        T temp = item;
        item = null;
        return temp;
    }
}
