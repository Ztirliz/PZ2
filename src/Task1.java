import java.io.*;
import java.util.Scanner;

public class Task1 {
    public static void main() {


        try {
            String line;
            // Відкриття файлу для читання
            BufferedReader reader = new BufferedReader(new FileReader("horsemen.txt"));
            // Відкриття файлу для запису
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            // Зчитування рядків з файлу

            while ((line = reader.readLine()) != null) {
                // Заміна символів окрім букв і пробілів
                line = line.replaceAll("[^а-яА-Я\\s]", "");
                // Заміна послідовностей пробілів
                line = line.replaceAll("\\s+", "");
                //Заміна ё на е
                line = line.replaceAll("ё", "е");
                // Видалення пробілів на початку та в кінці рядка
                line = line.trim();
                // Заміна прописних літер на стрічні
                line = line.toLowerCase();

                // Запис рядка у файл
                writer.write(line);
                writer.newLine();
            }

            // Закриття файлів
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text += line + "\n";
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }
        // Введення і редагування ключа
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть ключ: ");
        String key = scanner.nextLine().toLowerCase().replaceAll("[^а-я]", "");
        // Зчитування і шифрування тексту
        String ciphertext = encrypt(text, key);
        try (FileWriter writeCipherText = new FileWriter("cipherText.txt")) {
            writeCipherText.write(ciphertext);
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
            return;
        }
        // Зчитування і розшифрування тексту
        String decryptedtext = decrypt(ciphertext, key);
        try (FileWriter writeDecryptText = new FileWriter("decryptText.txt")) {
            writeDecryptText.write(decryptedtext);
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
            return;
        }
        System.out.println("Заданий текст - horsemen.txt був відредагований, зашифрований та записаний у текстовий файл cipherText.txt \n та розшифрований - decryptText.txt");


    }

    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (c < 'а' || c > 'я') {
                continue;
            }
            ciphertext.append((char) ((c + key.charAt(j) - 2 * 'а') % 32 + 'а'));
            j = ++j % key.length();
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (c < 'а' || c > 'я') {
                continue;
            }
            plaintext.append((char) ((c - key.charAt(j) + 32) % 32 + 'а'));
            j = ++j % key.length();
        }
        return plaintext.toString();
    }

}
