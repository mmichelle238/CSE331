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

package setup;

import java.lang.Iterable;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Collections;

/**
 * This is a container can be used to contain Balls.
 * A given Ball may only appear in a BallContainer once.
 */
public class BallContainer implements Iterable<Ball> {

    /**
     * Contents of the BallContainer.
     */
    private Set<Ball> contents;

    /**
     * Constructor that creates a new BallContainer.
     */
    public BallContainer() {
        // Normally, you'd expect to see <Ball> instead of just <>, here.
        // <> is called the diamond operator, it was added in Java 7 as a
        // programming convenience. Since contents was already declared
        // as a Set<Ball>, the Java compiler already knows that the type should
        // be Ball and including it here is considered redundant. The diamond
        // operator is used where the compiler can infer what the type should be
        // based on other information.
        contents = new LinkedHashSet<>();
    }

    /**
     * Implements the Iterable interface for this container.
     *
     * @return an Iterator over the Ball objects contained
     * in this container.
     */
    @Override
    public Iterator<Ball> iterator() {
        // If we just returned the iterator of "contents", a client
        // could call the remove() method on the iterator and modify
        // it behind our backs.  Instead, we wrap contents in an
        // "unmodifiable set"; calling remove() on this iterator
        // throws an exception.  This is an example of avoiding
        // "representation exposure."  You will learn more about this
        // concept later in the course.
        return Collections.unmodifiableSet(contents).iterator();
    }

    /**
     * Adds a ball to the container. This method returns <code>true</code>
     * if ball was successfully added to the container, i.e. ball is
     * not already in the container. Of course, you are allowed to put
     * a Ball into a container only once. Hence, this method returns
     * <code>false</code>, if ball is already in the container.
     *
     * @param b Ball to be added.
     * @return true if ball was successfully added to the container,
     * i.e. ball is not already in the container. Returns false, if ball is
     * already in the container.
     * @spec.requires b != null.
     */
    public boolean add(Ball b) {
        // Your code goes here.  Remove the exception after you're done.
        //throw new RuntimeException("Method not implemented");
        if(contents.contains(b)) {
            return false;
        } else {
            contents.add(b);
            return true;
        }
    }

    /**
     * Removes a ball from the container. This method returns
     * <code>true</code> if ball was successfully removed from the
     * container, i.e. ball is actually in the container. You cannot
     * remove a Ball if it is not already in the container and so ths
     * method will return <code>false</code>, otherwise.
     *
     * @param b Ball to be removed.
     * @return true if ball was successfully removed from the container,
     * i.e. ball is actually in the container. Returns false, if ball is not
     * in the container.
     * @spec.requires b != null.
     */
    public boolean remove(Ball b) {
        // Your code goes here.  Remove the exception after you're done.
        // throw new RuntimeException("Method not implemented");
        if(!contents.contains(b)) {
            return false;
        } else {
            contents.remove(b);
            return true;
        }
    }

    /**
     * Each Ball has a volume. This method returns the total volume of
     * all the Balls in the container.
     *
     * @return the volume of the contents of the container.
     */
    public double getVolume() {
        // Your code goes here.  Remove the exception after you're done.
        //throw new RuntimeException("Method not implemented");
        double totalVolume = 0;
        for (Ball b: contents) {
            totalVolume += b.getVolume();
        }
        return totalVolume;
    }

    /**
     * Returns the number of Balls in this container.
     *
     * @return the number of Balls in this container.
     */
    public int size() {
        // Your code goes here.  Remove the exception after you're done.
        //throw new RuntimeException("Method not implemented");
        return contents.size();
    }

    /**
     * Empties the container, i.e. removes all its contents.
     */
    public void clear() {
        // Your code goes here.  Remove the exception after you're done.
        //throw new RuntimeException("Method not implemented");
        contents.clear();
    }

    /**
     * This method returns <code>true</code> if this container contains
     * the specified Ball. It will return <code>false</code> otherwise.
     *
     * @param b Ball to be checked if its in container
     * @return true if this container contains the specified Ball. Returns
     * false, otherwise.
     * @spec.requires b != null.
     */
    public boolean contains(Ball b) {
        // Your code goes here.  Remove the exception after you're done.
        //throw new RuntimeException("Method not implemented");
        return contents.contains(b);
    }

}
