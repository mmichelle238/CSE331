/*
 * Copyright (C) 2020 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package graph.problem1;

/**
 * IntQueue2 is our second implementation of a basic first-in, first-out queue for Integers.
 *
 * <p>An IntQueue can be described as [n1, n2, ..., n_k], where n1 is the least-recently-added item
 * in the queue and is the next item to be removed. n_k is the most-recently-added and will be the
 * last of the current elements to be removed.
 *
 * <p>An IntQueue can also be described constructively, with the append operation, ':', such that
 * [n1, n2, ..., n_k] : n_k+1 is the result of enqueing n_k+1 at the end of the queue.
 */
public final class IntQueue2 {
    // This class represents a queue as a circular ring buffer. An array
    // stores the values in the queue. Because the number of elements
    // currently in the queue is usually less than the size of the
    // array, we store the index of the first item in the queue and the
    // total number of elements in the queue. For example, a queue with 4
    // items might look like this:
    //
    // [__ __ n1 n2 n3 n4 __ __]
    //        ^front   ^front+size-1
    //
    // As items are enqueued, front remains unchanged while size is
    // incremented.  As items are dequeued, front is incremented and size
    // is decremented.

    // Normally, your abstraction function and representation invarant would go
    // here. For ease of grading, please place them in your written answers.
    // instead with your answers to the other written exercises.

    /**
     * Starting size for the array.
     */
    private static final int INITIAL_SIZE = 20;

    /**
     * Elements in the queue.
     */
    int[] entries;
    /**
     * Pointer to the first element of the queue.
     */
    int front;
    /**
     * The number of elements in the queue.
     */
    int size;

    /**
     * @spec.effects constructs an empty queue
     */
    public IntQueue2() {
        entries = new int[INITIAL_SIZE];
        front = 0;
        size = 0;
        checkRep();
    }

    /**
     * Enqueue an item.
     *
     * @param entry item to be added to the queue
     * @spec.modifies this
     * @spec.effects places entry at the end of the queue
     */
    public void enqueue(Integer entry) {
        // Enlarge queue if necessary
        if (size == entries.length) {
            int[] newEntries = new int[entries.length * 2];
            for (int i = 0; i < size; i++) {
                newEntries[i] = entries[(front + i) % entries.length];
            }
            entries = newEntries;
            front = 0;
        }

        // Add item to the end of the queue, wrapping around to the front if necessary
        entries[(front + size) % entries.length] = entry;
        size++;

        checkRep();
    }

    /**
     * Dequeue an item.
     *
     * @return the item that was first in the queue
     * @spec.requires size() &gt; 0
     * @spec.modifies this
     * @spec.effects removes the item at the front of the queue
     */
    public Integer dequeue() {
        Integer ret = entries[front];
        size--;
        front = (front + 1) % entries.length;
        return ret;
    }

    /**
     * See the next item without removing it.
     *
     * @return the item currently first in the queue
     * @spec.requires size() &gt; 0
     */
    public Integer front() {
        return entries[front];
    }

    /**
     * @return number of elements in the queue
     */
    public int size() {
        return size;
    }

    /**
     * @return size() == 0
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        // If I gave this to you, you wouldn't have the fun of figuring out the
        // rep invariant for yourself :)
    }
}
