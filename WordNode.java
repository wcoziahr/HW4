package coziahr;

public class WordNode {
    String word;
    WordNode left, right;

    public WordNode(String word) {
        this.word = word;
        left = right = null;
    }
}