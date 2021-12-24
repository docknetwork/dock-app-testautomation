package dock.utilities;

import java.security.SecureRandom;
import java.util.Random;

public class KFZStringUtils {
    private static final char[] CHARSET_AZ09 = "abcdefghijklmnopqrstuvwyxz0123456789".toCharArray();
    private static final char[] CHARSET_09 = "0123456789".toCharArray();

    public static String generatePhoneNumber() {
        long rand8DigitNumber = (long) Math.floor(Math.random() * 90000000L) + 10000000L;
        return String.valueOf(rand8DigitNumber);
    }

    public static String generateRandomIntegerWithinRange(int minimumRange, int maximumRange) {
        Random rn = new Random();
        int range = maximumRange - minimumRange + 1;
        int randomNum = rn.nextInt(range) + minimumRange;
        return String.valueOf(randomNum);
    }

    private static String generateAlphaNumericString(int length, char[] charset) {
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            int randomCharIndex = random.nextInt(charset.length);
            result[i] = charset[randomCharIndex];
        }
        return new String(result);
    }

    public static String generateAlphaNumericString(int length) {
        return generateAlphaNumericString(length, CHARSET_AZ09);
    }

    public static String getStringTitleCase(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    public static String generateAlphaString(int length) {
        return generateAlphaNumericString(length, CHARSET_09);
    }
}






