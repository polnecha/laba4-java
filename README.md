# Нечаева Полина ИТ-3,4 — Лабораторная работа №4

## Задание 1

### Задача 3
#### Текст задачи
Создайте ссылочный тип `Сравнимое`, гарантирующий наличие по данной ссылке метода со следующими характеристиками:
- Называется “сравнить”
- Принимает объект
- Тип принимаемого объекта может быть изменён без изменения самого `Сравнимого`
- Возвращает целое число

#### Алгоритм решения
Интерфейс Sravnimoe:
```java
public interface Sravnimoe<T> {
    int sravnit(T other);
}
```

### Задача 4
#### Текст задачи
Создайте сущность `Студент` из задачи 2.1.9. Выполните реализацию `Студентом` метода “сравнить” из задачи 3.1.3 таким образом, чтобы:
- Если средняя оценка текущего студента больше, чем у того, с которым выполняется сравнение — возвращается `1`
- Если средние оценки сравниваемых студентов одинаковы — возвращается `0`
- Если средняя оценка текущего студента меньше, чем у того, с которым выполняется сравнение — возвращается `-1`

Создайте двух студентов и сравните их между собой.

#### Алгоритм решения
Класс Student, реализующий Sravnimoe:
```java
import java.util.Arrays;

public class Student implements Sravnimoe<Student> {

    private String name;
    private int[] grades;

    public Student(String name, int... grades) {
        this.name = name;
        this.grades = Arrays.copyOf(grades, grades.length);
    }

    public double average() {
        if (grades.length == 0) return 0;
        int sum = 0;
        for (int g : grades) sum += g;
        return (double) sum / grades.length;
    }

    @Override
    public int sravnit(Student other) {
        double a = this.average();
        double b = other.average();

        if (a > b) return 1;
        if (a == b) return 0;
        return -1;
    }

    @Override
    public String toString() {
        return name + " " + Arrays.toString(grades) +
                " (средняя: " + String.format("%.2f", average()) + ")";
    }
}
```

## Задание 2

### Задача 3
#### Текст задачи
Создайте метод, принимающий `Коробку` из задачи 3.1.1, и кладёт в неё трёхмерную точку координат (из задачи 2.1.5) с произвольными значениями. 
Метод должен позволять передавать `Коробку` с более чем одним видом параметризации.

#### Алгоритм решения
```java
// Класс Box — обобщённая коробка
public class Box<T> {
    private T item;

    public void put(T item) {
        if (this.item != null) {
            throw new IllegalStateException("Коробка не пуста!");
        }
        this.item = item;
    }

    public T take() {
        T temp = item;
        item = null;
        return temp;
    }

    public boolean isEmpty() {
        return item == null;
    }
}

// Класс Point3D — трёхмерная точка
public class Point3D {
    private int x, y, z;

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "{" + x + ";" + y + ";" + z + "}";
    }
}

// Метод, который кладёт точку в коробку
public static void putPointInBox(Box<? super Point3D> box, Point3D point) {
    box.put(point);
}

// Main — ввод с клавиатуры и демонстрация
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Box<Point3D> box = new Box<>();

        System.out.println("Введите координаты точки X, Y, Z:");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int z = scanner.nextInt();

        Point3D point = new Point3D(x, y, z);

        putPointInBox(box, point);

        Point3D taken = (Point3D) box.take();
        System.out.println("Точка из коробки: " + taken);
    }
}

```


## Задание 3

### Задача 1
#### Текст задачи
Разработайте такой метод, который будет принимать список значений типа `T` и объект, имеющий единственный метод `apply`. Данный метод надо применить к каждому элементу списка и вернуть новый список значений типа `P` (типы `T` и `P` могут как совпадать, так и отличаться).

Используйте разработанный метод следующим образом:
1. Передайте в метод список со значениями: `"qwerty"`, `"asdfg"`, `"zx"`, а получите список чисел, где каждое число соответствует длине каждой строки.
2. Передайте в метод список со значениями: `1`, `-3`, `7`, а получите список, в котором все отрицательные числа стали положительными, а положительные остались без изменений.
3. Передайте в метод список, состоящий из массивов целых чисел, а получите список, в котором будут только максимальные значения каждого из исходных массивов.

#### Алгоритм решения
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

// Класс с обобщённым методом map
public class GenericUtils {

    // Метод принимает список значений типа T и функцию Function<T,P>,
    // применяет её к каждому элементу и возвращает список типа P
    public static <T, P> List<P> map(List<T> list, Function<T, P> func) {
        List<P> result = new ArrayList<>();
        for (T item : list) {
            result.add(func.apply(item));
        }
        return result;
    }
}

// Main — демонстрация работы обобщённого метода
public class Main {
    public static void main(String[] args) {

        // Пример 1: строки → длины строк
        List<String> words = Arrays.asList("qwerty", "asdfg", "zx");
        List<Integer> lengths = GenericUtils.map(words, String::length);
        System.out.println("Длины строк: " + lengths);

        // Пример 2: отрицательные числа → положительные
        List<Integer> nums = Arrays.asList(1, -3, 7);
        List<Integer> positive = GenericUtils.map(nums, n -> n < 0 ? -n : n);
        System.out.println("Положительные числа: " + positive);

        // Пример 3: массивы → максимальные значения
        List<int[]> arrays = Arrays.asList(
                new int[]{1,2,3},
                new int[]{5,0,2},
                new int[]{-1,-5,0}
        );
        List<Integer> maxValues = GenericUtils.map(arrays, arr -> {
            int max = arr[0];
            for (int x : arr) if (x > max) max = x;
            return max;
        });
        System.out.println("Максимальные значения: " + maxValues);
    }
}
```

### Задача 2
#### Текст задачи
Разработайте метод, который будет принимать список значений типа `T` и объект, имеющий единственный метод `test` (принимает `T` и возвращает `boolean`). Верните новый список типа `T`, из которого удалены все значения, не прошедшие проверку условием.

Используйте разработанный метод следующим образом:
1. Передайте в метод список со значениями: `"qwerty"`, `"asdfg"`, `"zx"`, и отфильтруйте все строки, имеющие менее трёх символов.
2. Передайте в метод список со значениями: `1`, `-3`, `7`, и отфильтруйте все положительные элементы.
3. Передайте в метод список, состоящий из массивов целых чисел, и получите список, в котором будут только те массивы, в которых нет ни одного положительного элемента.

#### Алгоритм решения
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class GenericUtils {

    // Метод filter: возвращает новый список с элементами, прошедшими проверку
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // Пример 1: строки длиной >= 3
        List<String> words = Arrays.asList("qwerty", "asdfg", "zx");
        List<String> filteredWords = filter(words, s -> s.length() >= 3);
        System.out.println("Строки длиной >=3: " + filteredWords);

        // Пример 2: отрицательные числа
        List<Integer> nums = Arrays.asList(1, -3, 7);
        List<Integer> negative = filter(nums, n -> n < 0);
        System.out.println("Отрицательные числа: " + negative);

        // Пример 3: массивы без положительных элементов
        List<int[]> arrays = Arrays.asList(
            new int[]{1, -2, 0},
            new int[]{-1, -5, 0},
            new int[]{0, -3, -7}
        );
        List<int[]> filteredArrays = filter(arrays, arr -> {
            for (int x : arr) if (x > 0) return false;
            return true;
        });
        System.out.println("Массивы без положительных элементов:");
        for (int[] arr : filteredArrays) System.out.println(Arrays.toString(arr));
    }
}

```

### Задача 3
#### Текст задачи
Разработайте метод, который будет принимать список значений типа `T` и способ, с помощью которого список значений можно свести к одному значению типа `T`, которое и возвращается из метода.

Используйте разработанный метод следующим образом:
1. Передайте в метод список со значениями: `"qwerty"`, `"asdfg"`, `"zx"`, и сформируйте одну большую строку, состоящую из всех строк исходного списка.
2. Передайте в метод список со значениями: `1`, `-3`, `7`, и верните сумму всех значений исходного списка.
3. Имеется список, состоящий из списков целых чисел — получите общее количество элементов во всех списках.  
   *Подсказка:* решить задачу можно в одно действие или последовательно использовать методы из задач 3.3.1 и 3.3.3.

Далее необходимо изменить разработанный метод таким образом, чтобы он гарантированно не возвращал `null` и не выбрасывал ошибок, если исходный список пуст.

#### Алгоритм решения
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class GenericUtils {

    // Обобщённый метод reduce
    public static <T> T reduce(List<T> list, BinaryOperator<T> op) {
        if (list == null || list.isEmpty()) return null; // безопасно для пустого списка

        T result = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            result = op.apply(result, list.get(i));
        }
        return result;
    }
}

// Пример использования
class Main {
    public static void main(String[] args) {
        // Пример 1: объединение строк
        List<String> words = Arrays.asList("qwerty", "asdfg", "zx");
        String combined = GenericUtils.reduce(words, (a, b) -> a + b);
        System.out.println("Объединённые строки: " + combined);

        // Пример 2: сумма чисел
        List<Integer> numbers = Arrays.asList(1, -3, 7);
        Integer sum = GenericUtils.reduce(numbers, Integer::sum);
        System.out.println("Сумма чисел: " + sum);

        // Пример 3: общее количество элементов во вложенных списках
        List<List<Integer>> listOfLists = Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(3, 4, 5)
        );
        Integer totalElements = GenericUtils.reduce(
            GenericUtils.map(listOfLists, List::size),
            Integer::sum
        );
        System.out.println("Общее количество элементов: " + totalElements);
    }

    // Вспомогательный метод map для преобразования списка
    public static <T, P> List<P> map(List<T> list, java.util.function.Function<T, P> f) {
        List<P> result = new ArrayList<>();
        for (T item : list) result.add(f.apply(item));
        return result;
    }
}

```

### Задача 4
#### Текст задачи
Разработайте метод, который будет возвращать коллекцию типа `P` со значениями типа `T`. Метод принимает:
1. Список исходных значений
2. Способ создания результирующей коллекции
3. Способ передачи значений исходного списка в результирующую коллекцию

Используйте разработанный метод следующим образом:
1. Передайте в метод список со значениями: `1`, `-3`, `7`, и верните их разбитыми на два подсписка: в одном — только положительные числа, в другом — только отрицательные.
2. Передайте в метод список со значениями: `"qwerty"`, `"asdfg"`, `"zx"`, `"qw"`, и верните их разбитыми на подсписки так, чтобы в любом подсписке были строки только одинаковой длины.
3. Передайте в метод список со значениями: `"qwerty"`, `"asdfg"`, `"qwerty"`, `"qw"`, и верните набор, который не может содержать одинаковые объекты (например, `Set`).

#### Алгоритм решения
```java
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class GenericUtils {

    // Универсальный метод collect
    public static <T, P> P collect(List<T> list, Supplier<P> creator, BiConsumer<P, T> collector) {
        P result = creator.get();
        for (T item : list) {
            collector.accept(result, item);
        }
        return result;
    }

    public static void main(String[] args) {

        // ----------------- Пример 1: числа → два подсписка -----------------
        List<Integer> nums = Arrays.asList(1, -3, 7, -2);
        List<List<Integer>> split = GenericUtils.collect(nums, ArrayList::new, (outer, n) -> {
            List<Integer> inner;
            if (n > 0) {
                if (outer.isEmpty()) outer.add(new ArrayList<>());
                inner = outer.get(0); // положительные
            } else {
                if (outer.size() < 2) outer.add(new ArrayList<>());
                inner = outer.get(1); // отрицательные
            }
            inner.add(n);
        });
        System.out.println("Разделение чисел: " + split);

        // ----------------- Пример 2: строки → подсписки одинаковой длины -----------------
        List<String> words = Arrays.asList("qwerty", "asdfg", "zx", "qw");
        Map<Integer, List<String>> byLength = GenericUtils.collect(words, HashMap::new, (map, str) -> {
            map.computeIfAbsent(str.length(), k -> new ArrayList<>()).add(str);
        });
        System.out.println("Строки по длине: " + byLength);

        // ----------------- Пример 3: уникальные строки -----------------
        List<String> dupWords = Arrays.asList("qwerty", "asdfg", "qwerty", "qw");
        Set<String> unique = GenericUtils.collect(dupWords, HashSet::new, Set::add);
        System.out.println("Уникальные строки: " + unique);
    }
}
```