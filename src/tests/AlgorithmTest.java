
package tests;

import algorithms.AhoCorasick;
import algorithms.KMP;

public class AlgorithmTest {

    public static void run() {
        test("ahishers",
                new String[]{"he", "she", "his", "hers"}, 4);

        test("aaaa",
                new String[]{"a", "aa", "aaa"}, 9);

        test("abcdef",
                new String[]{"xyz", "123"}, 0);

        test("banana",
                new String[]{"ana", "na"}, 4);

        test("abcabc",
                new String[]{"abc", "bc", "c"}, 6);

        System.out.println("All 5 tests passed!");
    }

    private static void test(
            String text, String[] patterns, int expected) {

        AhoCorasick ac = new AhoCorasick();

        for (String pattern : patterns) {
            ac.addPattern(pattern);
        }

        ac.buildFailureLinks();

        int acMatches = ac.search(text);
        int kmpMatches = 0;

        for (String pattern : patterns) {
            kmpMatches += KMP.search(text, pattern);
        }

        if (acMatches != expected ||
                kmpMatches != expected) {
            throw new AssertionError(
                    "Test failed for: " + text
            );
        }

        System.out.println("PASS: " + text
                + " | Matches: " + acMatches);
    }
}
