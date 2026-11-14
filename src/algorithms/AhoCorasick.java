
package algorithms;

import java.util.*;

public class AhoCorasick {

    private static class Node {
        Map<Character, Node> children = new HashMap<>();
        Node fail;
        List<String> output = new ArrayList<>();
    }

    private final Node root = new Node();

    public void addPattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Empty pattern");
        }

        Node current = root;

        for (char c : pattern.toCharArray()) {
            current = current.children.computeIfAbsent(
                    c, k -> new Node()
            );
        }

        current.output.add(pattern);
    }

    public void buildFailureLinks() {
        Queue<Node> queue = new ArrayDeque<>();
        root.fail = root;

        for (Node child : root.children.values()) {
            child.fail = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (Map.Entry<Character, Node> entry
                    : current.children.entrySet()) {

                char c = entry.getKey();
                Node child = entry.getValue();
                Node failure = current.fail;

                while (failure != root &&
                        !failure.children.containsKey(c)) {
                    failure = failure.fail;
                }

                if (failure.children.containsKey(c)) {
                    child.fail = failure.children.get(c);
                } else {
                    child.fail = root;
                }

                child.output.addAll(child.fail.output);
                queue.add(child);
            }
        }
    }

    public int search(String text) {
        int matches = 0;
        Node current = root;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            while (current != root &&
                    !current.children.containsKey(c)) {
                current = current.fail;
            }

            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            }

            matches += current.output.size();
        }

        return matches;
    }

    public List<String> searchWithPositions(String text) {
        List<String> matches = new ArrayList<>();
        Node current = root;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            while (current != root &&
                    !current.children.containsKey(c)) {
                current = current.fail;
            }

            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            }

            for (String pattern : current.output) {
                int start = i - pattern.length() + 1;

                matches.add(
                        pattern + " found at index " + start
                );
            }
        }

        return matches;
    }
}
