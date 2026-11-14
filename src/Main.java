
import algorithms.AhoCorasick;
import experiments.Benchmark;
import tests.AlgorithmTest;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Algorithm Tests ===");
        AlgorithmTest.run();

        System.out.println();
        System.out.println("=== Match Positions ===");

        AhoCorasick ac = new AhoCorasick();

        ac.addPattern("he");
        ac.addPattern("she");
        ac.addPattern("his");
        ac.addPattern("hers");

        ac.buildFailureLinks();

        String text = "ahishers";

        for (String match : ac.searchWithPositions(text)) {
            System.out.println(match);
        }

        System.out.println();
        System.out.println("=== Benchmark Results ===");

        Benchmark.run();
    }
}
