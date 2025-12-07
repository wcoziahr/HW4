package coziahr;
import java.util.*;

public class Dictionary {
    private WordNode root;

    public void insertWordNode(String word) {
        root = insert(root, word);
    }

    private WordNode insert(WordNode node, String word) {
        if (node == null) return new WordNode(word);
        int cmp = word.compareTo(node.word);
        if (cmp < 0) node.left = insert(node.left, word);
        else if (cmp > 0) node.right = insert(node.right, word);
        // Ignore duplicates
        return node;
    }

    public boolean spellCheck(String word) {
        return search(root, word) != null;
    }

    private WordNode search(WordNode node, String word) {
        if (node == null) return null;
        int cmp = word.compareTo(node.word);
        if (cmp == 0) return node;
        else if (cmp < 0) return search(node.left, word);
        else return search(node.right, word);
    }

    public boolean checkWord(String word) {
        int[] removed = new int[]{0};
        root = remove(root, word, removed);
        return removed[0] == 1;
    }

    private WordNode remove(WordNode node, String word, int[] removed) {
        if (node == null) return null;
        int cmp = word.compareTo(node.word);
        if (cmp < 0) {
            node.left = remove(node.left, word, removed);
        } else if (cmp > 0) {
            node.right = remove(node.right, word, removed);
        } else {
            removed[0] = 1;
            // No children
            if (node.left == null && node.right == null) return null;
            // One child
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            // Two children: find ireplacement
            WordNode minNode = minValue(node.right);
            node.word = minNode.word;
            node.right = remove(node.right, minNode.word, new int[1]);
        }
        return node;
    }

    private WordNode minValue(WordNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<String> inorder() {
        List<String> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(WordNode node, List<String> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.word);
        inorderHelper(node.right, result);
    }

    public boolean checkNoCycles() {
        Set<WordNode> visited = new HashSet<>();
        return checkNoCyclesHelper(root, visited);
    }

    private boolean checkNoCyclesHelper(WordNode node, Set<WordNode> visited) {
        if (node == null) return true;
        if (visited.contains(node)) return false;
        visited.add(node);
        return checkNoCyclesHelper(node.left, visited) && checkNoCyclesHelper(node.right, visited);
    }

    public boolean isBST() {
        return isBSTHelper(root, null, null);
    }

    private boolean isBSTHelper(WordNode node, String min, String max) {
        if (node == null) return true;
        if (min != null && node.word.compareTo(min) <= 0) return false;
        if (max != null && node.word.compareTo(max) >= 0) return false;
        return isBSTHelper(node.left, min, node.word) && isBSTHelper(node.right, node.word, max);
    }
}