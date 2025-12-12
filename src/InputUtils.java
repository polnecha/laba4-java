import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите целое число.");
            }
        }
    }

    public static int[] readGrades(String message) {
        System.out.print(message);
        String line = scanner.nextLine().trim();

        if (line.isEmpty()) return new int[0];

        String[] parts = line.split("[,\\s]+");
        int[] grades = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            try {
                grades[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                System.out.println("Оценка \"" + parts[i] + "\" некорректна. Ставлю 0.");
                grades[i] = 0;
            }
        }
        return grades;
    }
}
