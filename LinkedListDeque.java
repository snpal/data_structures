public class LinkedListDeque<T> {
    /**
     * Invariants:
     *     sentinel always points to the sentinel node.
     *     size always contains the number of items that have been added to the Deque.
     *     The first node (if it exists) is always at sentinel.nextItem.
     *     The last node (if it exists) is always at sentinel.prevItem.
     */
    private TNode sentinel;
    private int size;

    private class TNode {
        T firstItem;
        TNode nextItem;
        TNode prevItem;

        TNode(T first, TNode next, TNode prev) {
            firstItem = first;
            nextItem = next;
            prevItem = prev;
        }
    }

    public LinkedListDeque() {
        sentinel = new TNode(null, sentinel, sentinel);
        size = 0;
    }

    public void addFirst(T item) {
        TNode temp = sentinel.nextItem;
        sentinel.nextItem = new TNode(item, sentinel.nextItem, sentinel);
        if (size == 0) {
            sentinel.nextItem.nextItem = sentinel;
            sentinel.prevItem = sentinel.nextItem;
        } else {
            temp.prevItem = sentinel.nextItem;
        }
        size += 1;
    }

    public void addLast(T item) {
        TNode temp = sentinel.prevItem;
        sentinel.prevItem = new TNode(item, sentinel, sentinel.prevItem);
        if (size == 0) {
            sentinel.nextItem = sentinel.prevItem;
            sentinel.nextItem.prevItem = sentinel;
        } else {
            temp.nextItem = sentinel.prevItem;
        }
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        TNode current = this.sentinel;
        while (!current.nextItem.equals(sentinel)) {
            System.out.print(current.nextItem.firstItem + " ");
            current = current.nextItem;
        }
        System.out.printf("%n");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T toReturn = sentinel.nextItem.firstItem;
        TNode temp = sentinel.nextItem.nextItem;
        sentinel.nextItem = temp;
        sentinel.nextItem.prevItem = sentinel;
        size -= 1;
        return toReturn;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T toReturn = sentinel.prevItem.firstItem;
        TNode temp = sentinel.prevItem.prevItem;
        sentinel.prevItem = temp;
        sentinel.prevItem.nextItem = sentinel;
        size -= 1;
        return toReturn;
    }


    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            return getHelper(index, this.sentinel.nextItem);
        }
    }

    private T getHelper(int index, TNode first) {
        if (index == 0) {
            return first.firstItem;
        }
        return getHelper(index - 1, first.nextItem);
    }
}
