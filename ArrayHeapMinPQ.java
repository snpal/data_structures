package bearmaps;
import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private int size;
    private T[] items;
    private Double[] priorities;
    private HashMap<T, Node> hm;

    private static final double ARRAY_LOADFACTOR = 0.25;

    private class Node<T> {
        T item;
        double priority;
        int index;

        Node(T item, double priority, int index) {
            this.item = item;
            this.priority = priority;
            this.index = index;
        }

        double getPriority() {
            return this.priority;
        }

        int getIndex() {
            return this.index;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public ArrayHeapMinPQ() {
        this.items = (T[]) new Object[1];
        this.priorities = new Double[1];
        size = 0;
        hm = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (!this.contains(item)) {
            if (size == items.length) {
                resize(2 * items.length);
            }
            size++;
            if (size == 1) {
                this.items = (T[]) new Object[1];
                this.priorities = new Double[1];
            }
            items[size - 1] = item;
            priorities[size - 1] = priority;
            hm.put(item, new Node<>(item, priority, size - 1));
            swim(size - 1, priority);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean contains(T item) {
        return hm.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return items[0];
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T smallest = items[0];
        items[0] = items[size - 1];
        priorities[0] = priorities[size - 1];
        hm.get(items[0]).setIndex(0);

        items[size - 1] = null;
        priorities[size - 1] = null;
        size--;

        if (size > 0) {
            sink(0, priorities[0]);
        }

        hm.remove(smallest);

        if (((double) size / (double) items.length) < ARRAY_LOADFACTOR) {
            resize(items.length / 2);
        }

        return smallest;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (hm.containsKey(item)) {
            Node N = hm.get(item);
            int i = N.getIndex();
            double oldPriority = N.getPriority();
            N.setPriority(priority);
            priorities[i] = priority;

            if (oldPriority < priority) {
                sink(i, priority);
            } else {
                swim(i, priority);
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    /** Heap/array helper methods **/
    private void resize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        Double[] newPriorities = new Double[newCapacity];

        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
            newPriorities[i] = priorities[i];
        }
        items = newItems;
        priorities = newPriorities;
    }

    private void sink(int i, double priority) {
        if (2 * i + 2 <= size) {
            int child = minChild(i);
            if (priority > priorities[child]) {
                swap(i, child);
                sink(child, priorities[child]);
            }
        }
    }

    private void swim(int i, double priority) {
        // @source: lecture 21 slide 22
        if (priorities[parent(i)] > priority) {
            swap(i, parent(i));
            swim(parent(i), priorities[parent(i)]);
        }
    }

    private int parent(int i) {
        // @source: lecture 21 slide 22
        return (i - 1) / 2;
    }

    private int minChild(int i) {
        if (2 * i + 2 >= size) {
            return 2 * i + 1;
        }
        int[] children =  new int[] {2 * i + 1, 2 * i + 2};
        if (priorities[children[0]] < priorities[children[1]]) {
            return children[0];
        } else {
            return children[1];
        }
    }

    private void swap(int x, int y) {
        T tempItem = items[x];
        items[x] = items[y];
        items[y] = tempItem;
        Double tempPriority = priorities[x];
        priorities[x] = priorities[y];
        priorities[y] = tempPriority;
        Node nx = hm.get(items[x]);
        Node ny = hm.get(items[y]);
        nx.setIndex(x);
        ny.setIndex(y);
    }
}
