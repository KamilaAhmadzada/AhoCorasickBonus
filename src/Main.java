
import experiments.Benchmark;
import tests.AlgorithmTest;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Algorithm Tests ===");
        AlgorithmTest.run();

        System.out.println();
        System.out.println("=== Benchmark Results ===");
        Benchmark.run();
    }
}
