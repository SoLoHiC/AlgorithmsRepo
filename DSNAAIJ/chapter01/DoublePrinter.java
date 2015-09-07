package chapter01;

public class DoublePrinter {

    public static void printDigit(int n) {

        if (n >= 0 && n < 10) {
            System.out.print(n);
        }
    }

    public static void printInteger(int n) {

        if (n >= 10 || n <= -10) {
            printInteger(n / 10);
        }
        printDigit(n % 10);
    }

    public static void printFraction(double n) {

        if (n != 0) {
            printDigit(((int) (n * 10)) % 10);
            printFraction(n * 10 - (int) (n * 10));
        }
    }

    public static void printOut(double n) {

        printInteger((int) n);
        System.out.print('.');
        printFraction(n - (int) n);
    }

    public static void main(String[] args) {

        double x = 10.555;
        printOut(x);
    }
}
