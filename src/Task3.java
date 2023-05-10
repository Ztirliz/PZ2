import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Task3 {
    public static void main() {
        String text = "";
        String inputFilename = "cipher.txt";
        String outputFilename = "decryptText.txt";
        Map<Integer, Integer> keyIndexCounts = new HashMap<>();
        // Зчитування ШТ
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text += line;
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        //Обрахунок довжини ключа
        for (int i = 2; i <= 40; i++) {
            for (int j = 0; j <= text.length() - i; j++) {
                if (j + i >= text.length()) {
                    break;
                }
                char c1 = text.charAt(j);
                char c2 = text.charAt(j + i);
                if (Character.isAlphabetic(c1) && Character.isAlphabetic(c2) && c2 == c1) {
                    keyIndexCounts.put(i, keyIndexCounts.getOrDefault(i, 0) + 1);
                }
            }
        }

        int maxValue = Collections.max(keyIndexCounts.values());

        List<Integer> maxValueKeys = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : keyIndexCounts.entrySet()) {
            if (entry.getValue() == maxValue) {
                maxValueKeys.add(entry.getKey());
            }
        }
        int keyLength = getKey(keyIndexCounts,maxValue); // - довжина ключа


        Map<Character, Double> map = new HashMap<>();// - частота російських символів
        map.put('о', 0.10983);
        map.put('е', 0.08483);
        map.put('а', 0.07998);
        map.put('и', 0.07367);
        map.put('н', 0.067);
        map.put('т', 0.06318);
        map.put('с', 0.05473);
        map.put('р', 0.04746);
        map.put('в', 0.04533);
        map.put('л', 0.04343);
        map.put('к', 0.03486);
        map.put('м', 0.03203);
        map.put('д', 0.02977);
        map.put('п', 0.02804);
        map.put('у', 0.02615);
        map.put('я', 0.02001);
        map.put('ы', 0.01898);
        map.put('ь', 0.01735);
        map.put('г', 0.01687);
        map.put('з', 0.01641);
        map.put('б', 0.01592);
        map.put('ч', 0.0145);
        map.put('й', 0.01208);
        map.put('х', 0.00966);
        map.put('ж', 0.0094);
        map.put('ш', 0.00718);
        map.put('ю', 0.00639);
        map.put('ц', 0.00486);
        map.put('щ', 0.00361);
        map.put('э', 0.00331);
        map.put('ф', 0.00267);
        map.put('ъ', 0.00037);

        Map<Integer, Character> russianAlphabet = new HashMap<>(); // - російський алфавіт
        russianAlphabet.put(0, 'а');
        russianAlphabet.put(1, 'б');
        russianAlphabet.put(2, 'в');
        russianAlphabet.put(3, 'г');
        russianAlphabet.put(4, 'д');
        russianAlphabet.put(5, 'е');
        russianAlphabet.put(6, 'ж');
        russianAlphabet.put(7, 'з');
        russianAlphabet.put(8, 'и');
        russianAlphabet.put(9, 'й');
        russianAlphabet.put(10, 'к');
        russianAlphabet.put(11, 'л');
        russianAlphabet.put(12, 'м');
        russianAlphabet.put(13, 'н');
        russianAlphabet.put(14, 'о');
        russianAlphabet.put(15, 'п');
        russianAlphabet.put(16, 'р');
        russianAlphabet.put(17, 'с');
        russianAlphabet.put(18, 'т');
        russianAlphabet.put(19, 'у');
        russianAlphabet.put(20, 'ф');
        russianAlphabet.put(21, 'х');
        russianAlphabet.put(22, 'ц');
        russianAlphabet.put(23, 'ч');
        russianAlphabet.put(24, 'ш');
        russianAlphabet.put(25, 'щ');
        russianAlphabet.put(26, 'ъ');
        russianAlphabet.put(27, 'ы');
        russianAlphabet.put(28, 'ь');
        russianAlphabet.put(29, 'э');
        russianAlphabet.put(30, 'ю');
        russianAlphabet.put(31, 'я');
// Розподіл ШТ на окремі блоки
        List<String> textArray = new ArrayList<>();
        for (int i = 0;i<keyLength;i++)
        {
            String str ="";
            for (int j=0;j<text.length();j+=keyLength){
                if (i+j+1>text.length()){
                    break;
                }
                str+=text.substring(i+j,i+j+1);
            }
            textArray.add(str);

        }
        // Визначення ключа за функцією Mi(g)
        String key = "";
        for (int a = 0; a<keyLength; a++) {

            Map <Integer,Double> doubles = new HashMap<>();
            for (int i = 0; i < 32; i++) {
                double sum = 0.0;
                for (int j = 0; j < 32; j++) {
                  sum += countOccurrences(textArray.get(a),russianAlphabet.get((j+i)%32)) * map.get(russianAlphabet.get(j));
                }
                doubles.put(i,sum);

            }
          key += russianAlphabet.get(getMaxKey(doubles)); // - ключ

        }
        System.out.println(key);
        // Розшифрування тексту за допомогою методу та запис у текстовий файл
        String decryptedText = decrypt(text, key);
        try (FileWriter writeDecryptText = new FileWriter(outputFilename)) {
            writeDecryptText.write(decryptedText);
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
            return;
        }
        System.out.println(" ШТ з текстового файлу cipher.txt був успішно розшифрований у текстовий файл decrycptText.txt");



    }
    // Метод, що отримує ключ зі значення в map<Integer, Character>
    public static int countOccurrences(String str, char letter) {
        return (int) str.chars()
                .filter(ch -> ch == letter)
                .count();
    }

    // Метод, що отримує перший ключ за входження значення
    public static <K, V> K getKey(Map<K, V> map, V value)
    {
        return map.keySet()
                .stream()
                .filter(key -> value.equals(map.get(key)))
                .findFirst().get();
    }
    // Метод, що отримує найбільше значення і повертає ключ
    public static int getMaxKey(Map<Integer, Double> map) {
        double maxVal = Collections.max(map.values());
        int maxKey = 0;
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            if (entry.getValue() == maxVal) {
                maxKey = entry.getKey();
                break;
            }
        }

        return maxKey;
    }
    // Метод, що розшифровує текст з ШТ та ключа
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

