import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger counter3 = new AtomicInteger(0);
    public static AtomicInteger counter4 = new AtomicInteger(0);
    public static AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeCounter = new Thread(() -> {
            for (String word : texts) {
                if (isPalindrome(word)) {
                    counter(word.length());
                }
            }
        });

        Thread ascendingCounter = new Thread(() -> {
            for (String word : texts) {
                if (isAscending(word)) {
                    counter(word.length());
                }
            }
        });

        Thread sameLettersCounter = new Thread(() -> {
            for (String word : texts) {
                if (isSameLetters(word)) {
                    counter(word.length());
                }
            }
        });

        palindromeCounter.start();
        sameLettersCounter.start();
        ascendingCounter.start();

        palindromeCounter.join();
        sameLettersCounter.join();
        ascendingCounter.join();

        System.out.printf("Красивых слов с длиной 3: %d шт\n", counter3.get());
        System.out.printf("Красивых слов с длиной 4: %d шт\n", counter4.get());
        System.out.printf("Красивых слов с длиной 5: %d шт\n", counter5.get());

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    //  сгенерированное слово является палиндромом, т. е. читается одинаково как слева направо, так и справа налево, например, abba;
    static boolean isPalindrome(String word) {
        int length = word.length();
        for (int i = 0; i < (length / 2); i++) {
            if (word.charAt(i) != word.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    //сгенерированное слово состоит из одной и той же буквы, например, aaa;
    static boolean isSameLetters(String word) {
        char[] wordToArray = word.toCharArray();
        for (int i = 0; i < wordToArray.length - 1; i++) {
            if (wordToArray[i] != wordToArray[i + 1]) {
                return false;
            }
        }
        return true;
    }

    //  буквы в слове идут по возрастанию: сначала все a (при наличии), затем все b (при наличии), затем все c и т. д. Например, aaccc.
    static boolean isAscending(String word) {
        char[] wordToArray = word.toCharArray();
        for (int i = 0; i < wordToArray.length - 1; i++) {
            if (wordToArray[i] > wordToArray[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void counter(int wordLenght) {
        if (wordLenght == 3) {
            counter3.addAndGet(1);
        }
        if (wordLenght == 4) {
            counter4.addAndGet(1);
        }
        if (wordLenght == 5) {
            counter5.addAndGet(1);
        }
    }
}