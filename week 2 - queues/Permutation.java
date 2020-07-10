/* *****************************************************************************
 *  Name: Kevin Staniszewski
 *  Date: 6/22/2020
 *  Description: Algorithms Part 1, Programming Assignment 2 - Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int numberPrint = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        int index = 0;
        for (String s : randomizedQueue) {
            if (index == numberPrint) {
                break;
            }

            StdOut.println(s);
            index++;
        }
    }
}
