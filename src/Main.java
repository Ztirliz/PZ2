import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Integer number = 0;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введіть номер завдання \n1-програма, яка виконує шифрування та розшифрування шифром Віженера текстів російською мовою \n" +
                    "2-Дослідження поведінки індексу відповідності для шифротекстів \n" +
                    "3-Дешифрування заданого шифротексту \n" +
                    "4-Вихід з програми");
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();


                if (number == 1) {
                    Task1.main();
                    break;
                }
                if (number == 2) {
                    Task2.main();
                    break;
                }
                if (number == 3) {
                    Task3.main();
                    break;
                }
                if (number == 4) {
                    break;
                }
                else if (number>4){
                    System.out.println("Неправильно введений номер програми. Спробуйте ще раз!");
                }
            } else {
                System.out.println("Неправильно введений тип змінної!");
            }
        }

    }
}