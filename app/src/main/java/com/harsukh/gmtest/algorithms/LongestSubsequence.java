package com.harsukh.gmtest.algorithms;

/**
 * Created by harsukh on 5/21/17.
 */

public class LongestSubsequence {
    public static void main(String[] args) {
        System.out.println(longestSubsequence("This", "Tis",
                "This".length() - 1, "Tis".length() - 1));
        System.out.println(reverseNotRecursive("uibdfg@biu"));
    }

    static int longestSubsequence(String A, String B, int r, int s) {
        if (r == -1 || s == -1) return 0;
        if (A.toCharArray()[r] == B.toCharArray()[s])
            return 1 + longestSubsequence(A, B, r - 1, s - 1);
        else
            return Math.max(longestSubsequence(A, B, r - 1, s),
                    longestSubsequence(A, B, r, s - 1));
    }

    static String reverseWithoutAffectingSpecial(String word) {
        if (word.length() <= 0) return "";
        if (('A' <= word.charAt(word.length() - 1) &&
                word.charAt(word.length() - 1) <= 'Z') ||
                ('a' <= word.charAt(word.length() - 1) &&
                        'z' <= word.charAt(word.length() - 1))) {
            return word.substring(word.length() - 1, word.length() - 2) +
                    reverseWithoutAffectingSpecial(word.substring(0, word.length() - 2));
        } else {
            return reverseWithoutAffectingSpecial(word.substring(0,
                    word.length() - 2));
        }
    }

    static String reverseNotRecursive(String word) {
        int l = 0;
        int r = word.length() - 1;
        String reversed = "";
        while (l < r) {
            if (!isAlphabet(word.charAt(l))) {
                ++l;
            } else if (!isAlphabet(word.charAt(r))) {
                --r;
            } else {
                reversed = word.replace(word.charAt(l), word.charAt(r));
            }
        }
        return reversed;
    }

    static boolean isAlphabet(char s) {
        return ('A' <= s &&
                s <= 'Z') ||
                ('a' <= s &&
                        'z' <= s);
    }

    static int computePower(int a, int b) {
        if (b == 0) {
            return 1;
        }
        if (b % 2 == 0) {
            return computePower(a * a, b / 2);
        } else return computePower(a * a, b / 2) * a;
    }
}
