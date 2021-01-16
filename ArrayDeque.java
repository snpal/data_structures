public class ArrayDeque<T> {

    /**
     * Invariants:
     *     size always contains the number of items that have been added to the Deque.
     *     The first item (if it exists) is always at indexOfFront
     */
    private T[] items;
    private int size;
    private int indexOfFront;
    private int indexOfBack;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        indexOfFront = items.length - 1;
        indexOfBack = 0;
    }

    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = get(i);
        }
        indexOfFront = capacity - 1;
        indexOfBack = size;
        items = temp;
    }

    /* Add to the start of the Deque. */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (items[indexOfBack] == null) {
            indexOfBack = items.length - 1;
        }
        if (indexOfFront == 0) {
            indexOfFront = items.length - 1;
        }
        if (items[indexOfFront] != null) {
            indexOfFront -= 1;
        }
        items[indexOfFront] = item;
        size += 1;
    }
    /* Add to the end of the Deque. */
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (items[indexOfFront] == null) {
            indexOfFront = 0;
        }
        if (indexOfBack == items.length - 1) {
            indexOfBack = 0;
        }
        if (items[indexOfBack] != null) {
            indexOfBack += 1;
        }
        items[indexOfBack] = item;
        size += 1;
    }

    /* Return if the Deque is empty. */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Return the size of the Deque. */
    public int size() {
        return size;
    }

    /* Print all items in the Deque followed by a newline. */
    public void printDeque() {
        for (int i = indexOfFront; i < items.length; i++) {
            System.out.println(items[i] + " ");
        }
        for (int i = 0; i <= indexOfBack; i++) {
            System.out.println(items[i] + " ");
        }
        System.out.printf("%n");
    }

    /* Remove and return the first item in the Deque. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T toReturn = items[indexOfFront];
        items[indexOfFront] = null;
        if (indexOfFront != items.length - 1 && items[indexOfFront + 1] != null) {
            indexOfFront += 1;
        } else if (items[0] != null) {
            indexOfFront = 0;
        }
        size -= 1;
        if ((double) size / (double) items.length < 0.25 && items.length > 1) {
            resize((int) (items.length * 0.5));
        }
        return toReturn;
    }

    /* Remove and return the last item in the Deque. */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T toReturn = items[indexOfBack];
        items[indexOfBack] = null;
        if (indexOfBack != 0 && items[indexOfBack - 1] != null) {
            indexOfBack -= 1;
        } else if (items[items.length - 1] != null) {
            indexOfBack = items.length - 1;
        }
        size -= 1;
        if (((double) size / (double) items.length) < 0.25 && items.length > 1) {
            resize((int) (items.length * 0.5));
        }
        return toReturn;
    }

    /* Return the item at the given index of the Deque */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        if (index >= items.length - indexOfFront) {
            int i = index - (items.length - indexOfFront);
            return items[i];
        } else {
            return items[indexOfFront + index];
        }
    }
}
