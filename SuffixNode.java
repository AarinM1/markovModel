public class SuffixNode {
    char character;
    int frequency;
    SuffixNode next;

    public SuffixNode(char character) {
        this.character = character;
        this.frequency = 1;
        this.next = null;
    }
}
