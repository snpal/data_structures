import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> {

    private class Node {
        private K key;
        private V value;
        private Node leftNode;
        private Node rightNode;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node rootNode;
    private int size;

    public BSTMap() {
        this.rootNode = null;
        this.size = 8;
    }

    public void clear() {
        this.rootNode = null;
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        if (this.rootNode == null) {
            return false;
        }
        return containsHelper(key, rootNode);

    }

    private boolean containsHelper(K key, Node node) {
        if (node.key == key) {
            return true;
        }
        return containsHelper(key, node.leftNode) || containsHelper(key, node.rightNode);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return getHelper(key, rootNode);
    }

    private V getHelper(K key, Node root) {
        if (root == null) {
            return null;
        }

        int compared = key.compareTo(root.key);
        if (compared < 0) {
            return getHelper(key, root.leftNode);
        } else if (compared > 0) {
            return getHelper(key, root.rightNode);
        } else {
            return root.value;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return this.size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        this.rootNode = putHelper(key, value, rootNode);
    }

    private Node putHelper(K key, V value, Node root) {
        if (root == null) {
            size += 1;
            return new Node(key, value);
        }

        int compared = key.compareTo(root.key);

        if (compared < 0) {
            root.leftNode = putHelper(key, value, root.leftNode);
        } else if (compared > 0) {
            root.rightNode = putHelper(key, value, root.rightNode);
        } else {
            root.value = value;
        }

        return root;
    }
}
