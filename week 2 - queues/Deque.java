/* *****************************************************************************
 *  Name: Kevin Staniszewski
 *  Date: 6/22/2020
 *  Description: Algorithms Part 1, Programming Assignment 2 - Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int first;
    private int size;
    private Item[] s;

    // construct an empty deque
    public Deque() {
        first = 0;
        size = 0;
        s = (Item[]) new Object[1];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = first; i < s.length; i++) {
            if (s[i] != null) {
                copy[i - first] = s[i];
            }
        }

        for (int j = 0; j < first; j++) {
            if (s[j] != null) {
                copy[s.length - first + j] = s[j];
            }
        }

        first = 0;
        s = copy;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }


        if (size == s.length) {
            resize(2 * s.length);
        }

        if (first == 0) {
            first = s.length - 1;
        }
        else {
            first = first - 1;
        }

        // StdOut.println("first " + first);

        s[first] = item;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == s.length) {
            resize(2 * s.length);
        }

        int lastPosition = 0;

        // StdOut.println("first " + first + " size " + size + " length " + s.length);
        lastPosition = ((first + size) % s.length) - 1;

        if (lastPosition == s.length - 1) {
            lastPosition = 0;
        }
        else {
            lastPosition = lastPosition + 1;
        }

        s[lastPosition] = item;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        if (size == (s.length / 4)) {
            resize(s.length / 2);
        }

        Item removedItem = s[first];
        s[first] = null;

        if (first == s.length - 1) {
            first = 0;
        }
        else {
            first = first + 1;
        }

        size--;

        return removedItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        if (size == (s.length / 4)) {
            resize(s.length / 2);
        }

        int lastPosition = 0;

        // StdOut.println("first " + first + " size " + size + " length " + s.length);
        lastPosition = ((first + size) % s.length) - 1;

        if (lastPosition == s.length - 1) {
            lastPosition = 0;
        }

        if (lastPosition < 0) {
            lastPosition = s.length - 1;
        }

        Item removedItem = s[lastPosition];
        s[lastPosition] = null;

        size--;

        return removedItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int current = first;
        private boolean aroundTheWorld = false;

        public boolean hasNext() {
            if (current == s.length) {
                aroundTheWorld = true;
                current = 0;
            }

            if (aroundTheWorld && (current == first)) {
                return false;
            }

            if (current < s.length) {
                return s[current] != null;
            }

            return false;
        }

        public Item next() {
            if (current == s.length) {
                aroundTheWorld = true;
                current = 0;
            }

            if (s[current] == null || (aroundTheWorld && (current == first))) {
                throw new NoSuchElementException();
            }

            Item item = s[current];
            current++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println("is it empty? " + deque.isEmpty());

        // deque.addFirst("hello");
        // StdOut.println("size? " + deque.size());
        // deque.addLast("hello1");
        // StdOut.println("size? " + deque.size());
        // deque.addLast("hello2");
        // StdOut.println("size? " + deque.size());
        // deque.addLast("hello3");
        // StdOut.println("size? " + deque.size());
        // StdOut.println("removed first " + deque.removeFirst());
        // StdOut.println("size? " + deque.size());
        // StdOut.println("removed first " + deque.removeFirst());
        // StdOut.println("size? " + deque.size());
        // StdOut.println("removed first " + deque.removeFirst());
        // StdOut.println("size? " + deque.size());
        //
        // StdOut.println("removed last " + deque.removeLast());
        // StdOut.println("size? " + deque.size());
        // StdOut.println("is it empty? " + deque.isEmpty());
        // StdOut.println("size? " + deque.size());
        // deque.addFirst("hello1");
        // StdOut.println("size? " + deque.size());
        // deque.addFirst("hello2");
        // StdOut.println("size? " + deque.size());
        // deque.addFirst("hello3");
        // StdOut.println("size? " + deque.size());
        //
        // StdOut.println("is it empty? " + deque.isEmpty());

        // deque.addFirst(2);
        // deque.addFirst(3);
        // // deque.addFirst(3);
        // StdOut.println(deque.removeLast());
        // StdOut.println(deque.removeLast());

        StdOut.println("is it empty? " + deque.isEmpty());
        deque.addLast(3);
        StdOut.println(deque.removeLast());
        deque.addLast(5);
        StdOut.println(deque.removeLast());
        deque.addLast(7);
        deque.addLast(8);
        StdOut.println(deque.removeLast());


        for (int j = 1; j <= 16; j++)
            deque.addFirst(j);
        Iterator<Integer> iterator = deque.iterator();
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());


        // for (int s : deque) {
        //     StdOut.println(s);
        // }
    }

}
