
package experiments;

import algorithms.AhoCorasick;
import algorithms.KMP;

import java.util.*;

public class Benchmark {

    private static final int[] PATTERN_COUNTS = {10, 100, 1000};
    private static final int[] TEXT_LENGTHS = {1000, 10000, 100000};
    private static final int RUNS = 5;
    private static final String ALPHABET = "ABCD";
    private static volatile int sink;

    public static void run() {
        Random random = new Random(42);

        System.out.println("Patterns,TextLength,AC_ms,KMP_ms,Matches");

        for (int patternCount : PATTERN_COUNTS) {
            List<String> patterns = generatePatterns(
                    patternCount, random
            );

            AhoCorasick ac = new AhoCorasick();

            for (String pattern : patterns) {
                ac.addPattern(pattern);
            }

            ac.buildFailureLinks();

            for (int textLength : TEXT_LENGTHS) {
                String text = randomString(textLength, random);

                double acTotal = 0;
                double kmpTotal = 0;
                int matches = 0;

                for (int run = 0; run < RUNS; run++) {
                    long start = System.nanoTime();
                    matches = ac.search(text);
                    sink = matches;
                    acTotal += System.nanoTime() - start;

                    start = System.nanoTime();

                    int kmpMatches = 0;
                    for (String pattern : patterns) {
                        kmpMatches += KMP.search(text, pattern);
                    }

                    sink = kmpMatches;
                    kmpTotal += System.nanoTime() - start;

                    if (matches != kmpMatches) {
                        throw new AssertionError(
                                "Match counts differ"
                        );
                    }
                }

                System.out.printf(
                        Locale.US,
                        "%d,%d,%.4f,%.4f,%d%n",
                        patternCount,
                        textLength,
                        acTotal / RUNS / 1_000_000.0,
                        kmpTotal / RUNS / 1_000_000.0,
                        matches
                );
            }
        }
    }

    private static List<String> generatePatterns(
            int count, Random random) {

        Set<String> patterns = new LinkedHashSet<>();

        while (patterns.size() < count) {
            patterns.add(randomString(6, random));
        }

        return new ArrayList<>(patterns);
    }

    private static String randomString(
            int length, Random random) {

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(
                    random.nextInt(ALPHABET.length())
            ));
        }

        return sb.toString();
    }
}
