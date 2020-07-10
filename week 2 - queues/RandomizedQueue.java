/* *****************************************************************************
 *  Name: Kevin Staniszewski
 *  Date: 6/22/2020
 *  Description: Algorithms Part 1, Programming Assignment 2 - Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] s;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        s = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < s.length; i++) {
            if (s[i] != null) {
                copy[i] = s[i];
            }
        }

        s = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == s.length) {
            resize(2 * s.length);
        }

        s[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        if (size == (s.length / 4)) {
            resize(s.length / 2);
        }

        int randomIndex = StdRandom.uniform(size);

        Item removedItem = s[randomIndex];
        s[randomIndex] = s[size - 1];
        s[size - 1] = null;
        size--;

        return removedItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniform(size);
        return s[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int index = 0;
        private Item[] copy = (Item[]) new Object[size];

        private ListIterator() {
            for (int i = 0; i < size; i++) {
                copy[i] = s[i];
            }

            StdRandom.shuffle(copy, 0, size);
        }

        public boolean hasNext() {
            return index < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = copy[index];
            index++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        // randomizedQueue.enqueue("hello");
        // StdOut.println("is it empty? " + randomizedQueue.isEmpty());
        // randomizedQueue.enqueue("hello1");
        // randomizedQueue.enqueue("hello2");
        // StdOut.println("size? " + randomizedQueue.size());
        // randomizedQueue.dequeue();
        // randomizedQueue.dequeue();
        // randomizedQueue.dequeue();
        //
        // StdOut.println("is it empty? " + randomizedQueue.isEmpty());
        //
        // StdOut.println("size? " + randomizedQueue.size());

        StdOut.println("size? " + randomizedQueue.size());
        randomizedQueue.enqueue("hello1");
        StdOut.println("size? " + randomizedQueue.size());
        randomizedQueue.enqueue("hello2");
        StdOut.println("size? " + randomizedQueue.size());

        StdOut.println("dequeue? " + randomizedQueue.dequeue());
        StdOut.println("dequeue? " + randomizedQueue.dequeue());

        // for (String s : randomizedQueue) {
        //     StdOut.println(s);
        // }
        //
        // for (String s : randomizedQueue) {
        //     for (String t : randomizedQueue) {
        //         StdOut.println("inner loop" + t);
        //     }
        //     StdOut.println(s);
        // }
    }
}
