import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Arthur on 2018/4/3.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    private class Node {
        public Item item;
        public Node next;
        public Node prior;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            if (current != null) {
                return true;
            }
            return false;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("no more items");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("error operation");
        }
    }


    public boolean isEmpty() {
        return first == null && last == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can't be null");
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        newNode.prior = null;
        if (isEmpty()) {
            last = newNode;
            first = newNode;
        } else {
            first.prior = newNode;
            first = newNode;
        }
        ++size;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can't be null");
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.prior = last;
        newNode.next = null;
        if (isEmpty()) {
            last = newNode;
            first = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        ++size;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("removeFirst from empty deque");
        }
        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prior = null;
        }
        --size;
        return item;

    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("removeLast from empty deque");
        }
        Item item = last.item;
        last = last.prior;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        --size;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
}
