import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * Created by Arthur on 2018/4/3.
 */
public class Permutation {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        int count = 0;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        try {
            while (true) {
                queue.enqueue(StdIn.readString());
            }
        } catch (NoSuchElementException e) {
        }
        for (String s : queue) {
            if (count == k) {
                break;
            } else {
                count++;
                StdOut.println(s);
            }
        }
    }
}
