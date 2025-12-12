import java.util.Arrays;

public class Student implements Sravnimoe<Student> {

    private String name;
    private int[] grades;

    public Student(String name, int... grades) {
        this.name = name;
        this.grades = Arrays.copyOf(grades, grades.length);
    }

    public String getName() {
        return name;
    }

    public int[] getGrades() {
        return Arrays.copyOf(grades, grades.length);
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
        return name + " " + Arrays.toString(grades);
    }
}
