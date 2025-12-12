import java.util.Arrays;
import java.util.List;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== МЕНЮ =====");
            System.out.println("1. Создать двух студентов и сравнить их");
            System.out.println("2. Работа с Коробкой и Точкой 3D (случайные координаты)");
            System.out.println("3. Демонстрация обобщённого метода (Функция)");
            System.out.println("4. Демонстрация фильтра");
            System.out.println("5. Демонстрация обобщённого метода (Сокращение)");
            System.out.println("6. Демонстрация коллекционирования");
            System.out.println("0. Выход");

            int choice = InputUtils.readInt("Ваш выбор: ");

            switch (choice) {
                case 1 -> compareStudents();
                case 2 -> boxDemo();
                case 3 -> demoGenericFunction();
                case 4 -> demoFilter();
                case 5 -> demoReduce();
                case 6 -> demoCollect();
                case 0 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Нет такого пункта!");
            }
        }
    }

    private static void compareStudents() {
        System.out.println("\n--- Студент 1 ---");
        String name1 = InputUtils.readString("Введите имя: ");
        int[] gr1 = InputUtils.readGrades("Введите оценки (через пробел/запятую): ");
        Student s1 = new Student(name1, gr1);

        System.out.println("\n--- Студент 2 ---");
        String name2 = InputUtils.readString("Введите имя: ");
        int[] gr2 = InputUtils.readGrades("Введите оценки (через пробел/запятую): ");
        Student s2 = new Student(name2, gr2);

        System.out.println("\nСтудент 1: " + s1);
        System.out.println("Студент 2: " + s2);

        int result = s1.sravnit(s2);

        System.out.println("\nРезультат сравнения:");
        if (result > 0) {
            System.out.println(s1 + " лучше учится.");
        } else if (result < 0) {
            System.out.println(s2 + " лучше учится.");
        } else {
            System.out.println("У студентов одинаковая успеваемость.");
        }
    }

    private static void boxDemo() {
        System.out.println("\n--- Демонстрация работы с коробкой ---");

        Box<Point3D> box3d = new Box<>();

        putRandomPoint(box3d);

        Point3D p = box3d.take();
        System.out.println("Из коробки извлечена точка: " + p);
    }

    public static void putRandomPoint(Box<? super Point3D> box) {
        int x = (int) (Math.random() * 10);
        int y = (int) (Math.random() * 10);
        int z = (int) (Math.random() * 10);

        box.put(new Point3D(x, y, z));
    }

    private static void demoGenericFunction() {
        System.out.println("\n--- Демонстрация обобщённого метода ---");

        List<String> words = Arrays.asList("qwerty", "asdfg", "zx");
        List<Integer> lengths = GenericUtils.map(words, String::length);
        System.out.println("Длины строк: " + lengths);

        List<Integer> nums = Arrays.asList(1, -3, 7);
        List<Integer> positive = GenericUtils.map(nums, n -> n < 0 ? -n : n);
        System.out.println("Положительные числа: " + positive);

        List<int[]> arrays = Arrays.asList(
                new int[]{1, 2, 3},
                new int[]{5, 0, 2},
                new int[]{-1, -5, 0}
        );
        List<Integer> maxValues = GenericUtils.map(arrays, arr -> {
            int max = arr[0];
            for (int x : arr) if (x > max) max = x;
            return max;
        });
        System.out.println("Максимальные значения: " + maxValues);
    }
    private static void demoFilter() {
        System.out.println("\n--- Демонстрация фильтра ---");

        List<String> words = Arrays.asList("qwerty", "asdfg", "zx");
        List<String> filteredWords = GenericUtils.filter(words, s -> s.length() >= 3);
        System.out.println("Строки длиной >=3: " + filteredWords);

        List<Integer> nums = Arrays.asList(1, -3, 7);
        List<Integer> negative = GenericUtils.filter(nums, n -> n < 0);
        System.out.println("Отрицательные числа: " + negative);

        List<int[]> arrays = Arrays.asList(
                new int[]{1, -2, 0},
                new int[]{-1, -5, 0},
                new int[]{0, -3, -7}
        );
        List<int[]> filteredArrays = GenericUtils.filter(arrays, arr -> {
            for (int x : arr) {
                if (x > 0) return false;
            }
            return true;
        });

        System.out.println("Массивы без положительных элементов:");
        for (int[] arr : filteredArrays) {
            System.out.println(Arrays.toString(arr));
        }
    }
    private static void demoReduce() {
        System.out.println("\n--- Демонстрация обобщённого метода Reduce ---");

        List<String> words = Arrays.asList("qwerty", "asdfg", "zx");
        String combined = GenericUtils.reduce(words, (a, b) -> a + b, "");
        System.out.println("Объединённые строки: " + combined);

        List<Integer> nums = Arrays.asList(1, -3, 7);
        Integer sum = GenericUtils.reduce(nums, (a, b) -> a + b, 0);
        System.out.println("Сумма чисел: " + sum);

        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4, 5),
                Arrays.asList()
        );
        List<Integer> sizes = GenericUtils.map(listOfLists, List::size);
        Integer totalCount = GenericUtils.reduce(sizes, (a, b) -> a + b, 0);
        System.out.println("Общее количество элементов: " + totalCount);
    }
    private static void demoCollect() {
        System.out.println("\n--- Демонстрация коллекционирования ---");

        List<Integer> nums = Arrays.asList(1, -3, 7, -5);
        List<Integer> positive = GenericUtils.collect(nums, ArrayList::new, (col, x) -> {
            if (x > 0) col.add(x);
        });
        List<Integer> negative = GenericUtils.collect(nums, ArrayList::new, (col, x) -> {
            if (x < 0) col.add(x);
        });
        System.out.println("Положительные: " + positive);
        System.out.println("Отрицательные: " + negative);

        List<String> words = Arrays.asList("qwerty", "asdfg", "zx", "qw");
        Map<Integer, List<String>> byLength = GenericUtils.collect(words, HashMap::new, (map, str) -> {
            map.computeIfAbsent(str.length(), k -> new ArrayList<>()).add(str);
        });
        System.out.println("Группировка по длине: " + byLength);

        List<String> wordsWithDuplicates = Arrays.asList("qwerty", "asdfg", "qwerty", "qw");
        Set<String> unique = GenericUtils.collect(wordsWithDuplicates, HashSet::new, Collection::add);
        System.out.println("Уникальные строки: " + unique);
    }
}
