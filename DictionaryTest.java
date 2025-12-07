package coziahr;
import java.util.*;

public class DictionaryTest {
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        String[] words = {"pear", "apple", "orange", "banana", "cherry", "fig"};

        // Insert all and check BST, no cycles, sorted inorder
        for (String word: words) dict.insertWordNode(word);
        assert dict.isBST() : "BST property violated after insertion";
        assert dict.checkNoCycles() : "Cycle detected after insertion";
        assert dict.inorder().equals(Arrays.asList("apple", "banana", "cherry", "fig", "orange", "pear")) : "Inorder incorrect";

        // Duplicate insert
        dict.insertWordNode("banana");
        assert dict.inorder().equals(Arrays.asList("apple", "banana", "cherry", "fig", "orange", "pear"));

        // Spell check
        assert dict.spellCheck("banana");
        assert !dict.spellCheck("lime");

        // Remove leaf node
        assert dict.checkWord("fig");
        assert dict.inorder().equals(Arrays.asList("apple", "banana", "cherry", "orange", "pear"));
        assert dict.isBST();
        assert dict.checkNoCycles();

        // Remove node with one child
        dict.insertWordNode("date");
        assert dict.checkWord("cherry");
        assert dict.inorder().equals(Arrays.asList("apple", "banana", "date", "orange", "pear"));
        assert dict.isBST();
        assert dict.checkNoCycles();

        // Remove node with two children ("orange")
        dict.insertWordNode("grape");
        dict.insertWordNode("elderberry");
        assert dict.checkWord("orange");
        assert dict.isBST();
        assert dict.checkNoCycles();
        assert dict.inorder().equals(Arrays.asList("apple", "banana", "date", "elderberry", "grape", "pear"));

        // Remove non-existent node
        assert !dict.checkWord("not_in_tree");

        // Remove all
        for (String w: new ArrayList<>(dict.inorder()))
            dict.checkWord(w);
        assert dict.inorder().isEmpty();

        // No cycles with single nodes
        dict.insertWordNode("x");
        dict.insertWordNode("y");
        dict.insertWordNode("z");
        assert dict.checkNoCycles();

        System.out.println("Successful");
    }
}