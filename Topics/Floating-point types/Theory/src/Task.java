// You can experiment here, it won’t be checked

import java.util.Scanner;

public class Task {
  public static void main(String[] args) {
    // put your code here
    Scanner scanner = new Scanner(System.in);
    double a = scanner.nextDouble();
    double b = scanner.nextDouble();
    double c = scanner.nextDouble();
    double x = (c - b) / a;
    System.out.println(x);
  }
}
