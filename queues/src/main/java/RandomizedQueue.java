import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by Arthur on 2018/4/3.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int size;

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        size = 0;
    }

    private class RandomizeQueueIterator implements Iterator<Item> {
        private int[] rand;
        private int current;

        public RandomizeQueueIterator() {
            rand = new int[size];
            for (int i =0; i < size; i++) {
                rand[i] = i;
            }
            StdRandom.shuffle(rand);
            current = 0;
        }

        public boolean hasNext() {
            if (current != rand.length) {
                return true;
            }
            return false;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no element for iterator");
            }
            return q[rand[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("not support remove");
        }
    }



    private void resizing(int capacity) {
        Item[] newQueue = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newQueue[i] = q[i];
        }
        q = newQueue;
    }

    public Iterator<Item> iterator() {
        return new RandomizeQueueIterator();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("call enqueue() with null argument");
        }
        if (size == q.length) {
            resizing(q.length * 2);
        }
        q[size++] = item;

    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("call dequeue when queue is empty");
        }
        if (size == q.length / 4) {
            resizing(q.length / 2);
        }
        int random = StdRandom.uniform(size);
        Item item = q[random];
        q[random] = q[--size];
        q[size] = null;
        return item;

    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("call dequeue when queue is empty");
        }
        return q[StdRandom.uniform(size)];
    }

}
