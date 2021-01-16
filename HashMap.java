import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {

    private class Node {
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private double loadFactor;
    private int size;
    private Set<K> keys;
    private ArrayList<Node>[] buckets;

    public MyHashMap() {
        loadFactor = 0.75;
        size = 0;
        buckets = (ArrayList<Node>[]) new ArrayList[16];
        keys = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        loadFactor = 0.75;
        size = 0;
        buckets = (ArrayList<Node>[]) new ArrayList[initialSize];
        keys = new HashSet<>();
    }
    public MyHashMap(int initialSize, double loadFactor) {
        this.loadFactor = loadFactor;
        size = 0;
        buckets = (ArrayList<Node>[]) new ArrayList[initialSize];
        keys = new HashSet<>();
    }

    @Override
    public void clear() {
        size = 0;
        buckets = (ArrayList<Node>[]) new Object[16];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        Node N = getNode(key);
        if (N != null) {
            N.value = value;
            return;
        }

        if ((double)(size) / buckets.length > loadFactor) {
            resize();
        }
        int idx = Math.floorMod(key.hashCode(), buckets.length);
        ArrayList<Node> L = buckets[idx];
        if (L == null) {
            buckets[idx] = new ArrayList<Node>();
        }
        buckets[idx].add(new Node(key, value));
        keys.add(key);
        size++;
    }

    private void resize() {
        ArrayList<Node>[] newList = (ArrayList<Node>[]) new Object[buckets.length * 2];

        for (int i = 0; i < buckets.length; i++) {
            newList[i] = new ArrayList<>();
        }

        for (K key : keys) {
            newList[Math.floorMod(key.hashCode(), buckets.length)].add(getNode(key));
        }
        buckets = newList;
    }

    @Override
    public V get(K key) {
        Node N = getNode(key);
        if (N == null) {
            return null;
        }
        return N.value;
    }

    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    private Node getNode(K key) {
        int idx = Math.floorMod(key.hashCode(), buckets.length);
        ArrayList<Node> L = buckets[idx];
        if (L != null) {
            for (Node N : L) {
                if (N.key.equals(key)) {
                    return N;
                }
            }
        }
        return null;
    }
    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }
}
