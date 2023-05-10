import java.io.*;
import java.util.*;

public class Task2 {
    public static void main() {
        String stopWord = "";
        int count = 0;
        String text = "";
        ArrayList <Double> doubles = new ArrayList<>();

        String inputFilename = "horsemen.txt";
        String outputFilename = "output.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line=line.toLowerCase();
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (Character.isAlphabetic(c)) {
                        output.append(c);

                    }
                }
                writer.write(output.toString());
            }
        } catch (IOException e) {
            System.err.println("Помилка: " + e.getMessage());
        }


        try (BufferedReader reader = new BufferedReader(new FileReader(outputFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text += line + "\n";
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }
        double matchingIndex = calculateMatchingIndex(text);
        System.out.println("Індекс збігу ВТ: " + String.format("%.4f", matchingIndex));

        List<String> keysList = new ArrayList<>();

        while (stopWord != "stop") {
            count++;
            String nameOfTextFile = "cipherText" + count + ".txt";
            String key = "";

            while (count==1) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введіть 2-x значний ключ: ");
                key = scanner.nextLine().toLowerCase().replaceAll("[^а-я]", "");
                if (key.length()==2){
                    keysList.add(key);
                    break;
                }
                else
                {
                    System.out.println("Ви ввели не 2-x значний ключ. Спробуйте ще раз!");
                }
            }
            while (count==2) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введіть 3-x значний ключ: ");
                key = scanner.nextLine().toLowerCase().replaceAll("[^а-я]", "");
                if (key.length()==3){
                    keysList.add(key);
                    break;
                }
                else
                {
                    System.out.println("Ви ввели не 3-x значний ключ. Спробуйте ще раз!");
                }
            }
            while (count==3) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введіть 4-x значний ключ: ");
                key = scanner.nextLine().toLowerCase().replaceAll("[^а-я]", "");
                if (key.length()==4){
                    keysList.add(key);
                    break;
                }
                else
                {
                    System.out.println("Ви ввели не 4-x значний ключ. Спробуйте ще раз!");
                }
            }
            while (count==4) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введіть 5-x значний ключ: ");
                key = scanner.nextLine().toLowerCase().replaceAll("[^а-я]", "");
                if (key.length()==5){
                    keysList.add(key);
                    break;
                }
                else
                {
                    System.out.println("Ви ввели не 5-x значний ключ. Спробуйте ще раз!");
                }
            }
            while (count==5) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введіть 6-x значний ключ: ");
                key = scanner.nextLine().toLowerCase().replaceAll("[^а-я]", "");
                if (key.length()==6){
                    keysList.add(key);
                    break;
                }
                else
                {
                    System.out.println("Ви ввели не 6-x значний ключ. Спробуйте ще раз!");
                }
            }
            while (count==6) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введіть 10-x значний ключ: ");
                key = scanner.nextLine().toLowerCase().replaceAll("[^а-я]", "");
                if (key.length()==10){
                    keysList.add(key);
                    break;
                }
                else
                {
                    System.out.println("Ви ввели не 10-x значний ключ. Спробуйте ще раз!");
                }
            }
            while (count==7) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введіть 15-x значний ключ: ");
                key = scanner.nextLine().toLowerCase().replaceAll("[^а-я]", "");
                if (key.length()==15){
                    keysList.add(key);
                    break;
                }
                else
                {
                    System.out.println("Ви ввели не 15-x значний ключ. Спробуйте ще раз!");
                }
            }
            while (count==8) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введіть 20-x значний ключ: ");
                key = scanner.nextLine().toLowerCase().replaceAll("[^а-я]", "");
                if (key.length()==20){
                    keysList.add(key);
                    break;
                }
                else
                {
                    System.out.println("Ви ввели не 20-x значний ключ. Спробуйте ще раз!");
                }
            }
            if (count>8){
                break;
            }
            String ciphertext = encrypt(text, key);
            matchingIndex = calculateMatchingIndex(ciphertext);
            System.out.println("Отриманий ШТ з ВТ з текстового файлу horsemen.txt був записаний у текстовий файл cipherText"+count+".txt");
            doubles.add(matchingIndex);


            System.out.println("Індекс збігу ШТ: " + String.format("%.4f", matchingIndex));
            try (FileWriter writeCipherText = new FileWriter( nameOfTextFile)) {
                writeCipherText.write(ciphertext);
            } catch (IOException e) {
                System.out.println("Error writing output file: " + e.getMessage());
                return;
            }

        }
        try {
            BufferedWriter index = new BufferedWriter(new FileWriter("matchingIndex.txt"));
            matchingIndex = calculateMatchingIndex(text);
            index.write("Індекс збігу ВТ: " + String.format("%.4f", matchingIndex)+ "\n");

            for (int i =0 ;i<doubles.size();i++) {
                matchingIndex = doubles.get(i);
                index.write("Індекс збігу для тексту " + (i+1) + " з ключем  " + keysList.get(i) +" :" + String.format("%.4f", matchingIndex) + "\n");
            }
            index.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
    private static double calculateMatchingIndex(String text) {
        double n = text.length();
        Map<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < text.length() - 1; i++) {
            char c = text.charAt(i);
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }
        double matchingIndex = 0.0;
        double frequency;
        for (char c : charCounts.keySet()) {
            frequency =  charCounts.get(c) ;
            matchingIndex += (frequency*(frequency-1))/(n*(n-1));
        }

        return matchingIndex;
    }
}