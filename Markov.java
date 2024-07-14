import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Markov {
    String substring;
    int count;
    SuffixNode head;
    Map<Character, Integer> nextCharCounts; // A map to store the frequency of each character that follows the substring

    public Markov(String substring) {
        this.substring = substring;
        this.count = 0;
        this.head = null;
        this.nextCharCounts = new HashMap<>();
    }

    // Increment the freq count of the substring
    public void add() {
        this.count++;
    }

    public void add(char c) {
        // Add c to the nextCharCounts map
        if (nextCharCounts.containsKey(c)) {
            nextCharCounts.put(c, nextCharCounts.get(c) + 1);
        } 
        else {
            nextCharCounts.put(c, 1);
        }

        // Add c to the suffix linked list
        SuffixNode current = head;
        while (current != null) {
            // if c already exists, increase freq
            if (current.character == c) {
                current.frequency++;
                return;
            }
            // if not, add a new node
            if (current.next == null) {
                current.next = new SuffixNode(c);
                return;
            }
            current = current.next;
        }
        // if list empty, initialize head
        if (head == null) {
            head = new SuffixNode(c);
        }
    }

    // Generate a random character based on the freqs of characters
    public char random() {
        int total = 0;
        for (int value : nextCharCounts.values()) {
            total += value;
        }

        int rand = new Random().nextInt(total);
        // cumulative sum of frequencies
        int cumulative = 0;
        for (Map.Entry<Character, Integer> entry : nextCharCounts.entrySet()) {
            cumulative += entry.getValue();
            if (rand < cumulative) {
                return entry.getKey();
            }
        }
        return ' ';
    }

    // Return the string representation of the suffix counts
    public String suffixCountsToString() {
        StringBuilder sb = new StringBuilder();
        SuffixNode current = head;
        while (current != null) {
            sb.append(current.character).append(": ").append(current.frequency).append(" ");
            current = current.next;
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);  // Remove the trailing space
        return sb.toString();
    }

    // Return the string representation of the object
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Substring: ").append(substring).append(", Count: ").append(count).append(", Next Char Counts: [");
        SuffixNode current = head;
        while (current != null) {
            sb.append(current.character).append(": ").append(current.frequency).append(", ");
            current = current.next;
        }
        if (sb.length() > 2) sb.setLength(sb.length() - 2);  // Remove the trailing comma and space
        sb.append("]");
        return sb.toString();
    }
}
