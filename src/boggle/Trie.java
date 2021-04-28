package boggle;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Practice building this Trie here:
 * 
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 * 
 * @author jspacco
 *
 */
class Trie {
    private boolean isValid;
    private Map<Character, Trie> kids;
    

    /** Initialize your data structure here. */
    public Trie() {
        kids = new HashMap<>();
        isValid = false;
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        // base case
        if (word.length() == 0){
            isValid = true;
            return;
        }
        // recursive step
        Character first = word.charAt(0); // autoboxing
        String rest = word.substring(1);
        if (!kids.containsKey(first)) {
            kids.put(first, new Trie());
        }
        kids.get(first).insert(rest);
    }
    
    private Trie traverse(String word) {
    	if (word.length() == 0) {
    		return this;
    	}
    	char first = word.charAt(0);
    	String rest = word.substring(1);
    	if (!kids.containsKey(first)) {
    		return null;
    	}
    	return kids.get(first).traverse(rest);
    }
    
    /** Returns if the word is in the trie. */
    public boolean contains(String word) {
        Trie t = traverse(word);
        return t != null && t.isValid;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return traverse(prefix) != null;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
