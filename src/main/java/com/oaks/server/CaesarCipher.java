package com.oaks.server;

public class CaesarCipher {
    public static String caesarEncrypt(String input, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) ((c - base + shift) % 26 + base));
            } else {
                result.append(c); // keep non-letters unchanged
            }
        }
        return result.toString();
    }

    public static String caesarDecrypt(String input, int shift) {
        return caesarEncrypt(input, 26 - (shift % 26)); // reverse shift
    }
}
