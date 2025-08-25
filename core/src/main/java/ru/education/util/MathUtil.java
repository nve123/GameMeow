package ru.education.util;

public class MathUtil {
    private MathUtil() {
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
