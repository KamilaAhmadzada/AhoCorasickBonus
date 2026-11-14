
# Aho-Corasick Algorithm — Bonus Assignment

## 1. Project Overview

This project implements the Aho-Corasick algorithm in Java
for searching multiple patterns in a text simultaneously.

The project also implements the Knuth-Morris-Pratt (KMP)
algorithm to compare their performance.

## 2. Algorithms Implemented

### Aho-Corasick

Aho-Corasick is a multiple-pattern string matching algorithm.

It uses three main components:

1. Trie: Stores all patterns using shared prefixes.
2. Failure Links: Allow the algorithm to move to another
   valid state when a character does not match.
3. Output Lists: Store patterns that end at each node.

The algorithm scans the text and identifies all matches,
including overlapping occurrences.

### KMP

KMP searches for occurrences of one pattern in a text.

It uses an LPS (Longest Proper Prefix which is also Suffix)
array to avoid unnecessary comparisons.

To search for multiple patterns, KMP is executed separately
for each pattern.

## 3. Project Structure

AhoCorasickBonus/
- src/
    - algorithms/
        - AhoCorasick.java
        - KMP.java
    - experiments/
        - Benchmark.java
    - tests/
        - AlgorithmTest.java
    - Main.java
- results/
    - benchmark.txt
- README.md
- .gitignore

## 4. Implementation Details

### Trie Construction

Each pattern is inserted character by character into a Trie.

Patterns with common prefixes share the same nodes.

### Failure Link Construction

Failure links are constructed using Breadth-First Search (BFS).

If a transition is unavailable, the algorithm follows failure
links to find another possible matching state.

### Pattern Matching

The text is scanned from left to right.

Whenever a node contains output patterns, the algorithm
records the corresponding matches.

The starting position of a match is:

start = currentIndex - patternLength + 1

Positions use zero-based indexing.

## 5. Correctness Testing

The project includes tests for:

- Multiple overlapping patterns
- Repeated characters
- Text with no matching patterns
- Patterns that share suffixes
- Multiple matches at different positions

Example:

Text: ahishers

Patterns: he, she, his, hers

Expected matches:

his at index 1
she at index 3
he at index 4
hers at index 4

Both Aho-Corasick and KMP should find four matches.

## 6. Benchmark Configuration

Pattern counts:
10, 100, 1000

Text lengths:
1000, 10000, 100000 characters

Pattern length:
6 characters

Alphabet:
A, B, C, D

Random seed:
42

Warm-up runs:
3

Measured runs:
5

Timing method:
System.nanoTime()

The reported times are averages in milliseconds.

Aho-Corasick's Trie and failure links are constructed
before timing. The benchmark measures searching rather
than total preprocessing plus searching time.

## 7. Time Complexity

Definitions:

n = length of the text
P = total length of all patterns
k = number of patterns
z = number of reported matches
m = length of one KMP pattern

### Aho-Corasick

Trie construction: O(P)

Failure link construction:
Depends on the transition representation and the work
required to follow failure links and propagate outputs.

Search:
O(n + z) with a constant-time transition automaton.

The current Java implementation uses HashMap transitions
and failure-link traversal, so its search cost also depends
on the number of failure-link steps.

Space:
O(P + stored output entries), excluding the returned matches.

### KMP

LPS construction: O(m)

Searching one pattern: O(n)

Searching k patterns separately:

O(k * n + P)

Space for one KMP search: O(m)

## 8. Performance Comparison

Aho-Corasick searches for multiple patterns using one
shared Trie and failure-link structure.

KMP searches for each pattern separately.

As the number of patterns increases, repeated KMP scans
can become more expensive.

Aho-Corasick requires additional preprocessing and memory,
but it can be more efficient for searching many patterns
in the same text.

The measured performance depends on text length,
pattern count, pattern distribution, JVM optimization,
and the testing environment.

## 9. Experimental Results

The benchmark results are saved in:

results/benchmark.txt

Each experiment reports:

- Number of patterns
- Text length
- Average Aho-Corasick search time
- Average KMP search time
- Number of matches

## 10. How to Run

Requirements:
Java JDK 17 or later

Compile from the project root:

javac -d out src/algorithms/*.java src/experiments/*.java src/tests/*.java src/Main.java

Run:

java -cp out Main

Alternatively, open the project in IntelliJ IDEA
and run Main.java.

## 11. Conclusion

Aho-Corasick is useful when searching for many patterns
in the same text.

KMP is simpler for searching a single pattern.

The benchmark demonstrates how their performance changes
with different text lengths and numbers of patterns.
