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
import java.util.*;

/**
 * This is a container can be used to contain Balls. The key
 * difference between a BallContainer and a Box is that a Box has a
 * finite volume. Once a box is full, a client cannot put in more Balls.
 */
public class Box implements Iterable<Ball> {

    /**
     * ballContainer is used to internally store balls for this Box
     */
    private BallContainer ballContainer;
    double maxVolume;

    /**
     * Constructor that creates a new box.
     *
     * @param maxVolume Total volume of balls that this box can contain.
     */
    public Box(double maxVolume) {
        // Your code goes here.  Remove the exception after you're done.
        //throw new RuntimeException("Method not implemented");
        ballContainer = new BallContainer();
        this.maxVolume = maxVolume;
    }
    /**
     * Implements the Iterable interface for this box.
     *
     * @return an Iterator over the Ball objects contained
     * in this box.
     */
    @Override
    public Iterator<Ball> iterator() {
        return ballContainer.iterator();
    }

    /**
     * This method is used to add Ball objects to this box of
     * finite volume.  The method returns true if a ball is
     * successfully added to the box, i.e., ball is not already in the
     * box and if the box is not already full; and it returns false,
     * if ball is already in the box or if the box is too full to
     * contain the new ball.
     *
     * @param b Ball to be added.
     * @return true if ball was successfully added to the box,
     * i.e. ball is not already in the box and if the box is not
     * already full. Returns false, if ball is already in the box or
     * if the box is too full to contain the new ball.
     * @spec.requires b != null.
     */
    public boolean add(Ball b) {
        // Your code goes here.  Remove the exception after you're done.
        //throw new RuntimeException("Method not implemented");
        if(!ballContainer.contains(b) && maxVolume >= ballContainer.getVolume() + b.getVolume()) {
            return ballContainer.add(b);
        } else {
            return false;
        }
    }

    /**
     * This method returns an iterator that returns all the balls in
     * this box in ascending size, i.e., return the smallest Ball
     * first, followed by Balls of increasing size.
     *
     * @return an iterator that returns all the balls in this box in
     * ascending size.
     */
    public Iterator<Ball> getBallsFromSmallest() {
        // Your code goes here.  Remove the exception after you're done.
        //throw new RuntimeException("Method not implemented");
        Iterator<Ball> ballIterator = iterator();
        List<Ball> balls = new ArrayList<>();
        while(ballIterator.hasNext()) {
            balls.add(ballIterator.next());
        }
        Collections.sort(balls, Comparator.comparingDouble(Ball::getVolume));
        ballIterator = balls.iterator();
        return ballIterator;
    }

    /**
     * Removes a ball from the box. This method returns
     * <code>true</code> if ball was successfully removed from the
     * container, i.e. ball is actually in the box. You cannot
     * remove a Ball if it is not already in the box and so ths
     * method will return <code>false</code>, otherwise.
     *
     * @param b Ball to be removed.
     * @return true if ball was successfully removed from the box,
     * i.e. ball is actually in the box. Returns false, if ball is not
     * in the box.
     * @spec.requires b != null.
     */
    public boolean remove(Ball b) {
        return ballContainer.remove(b);
    }

    /**
     * Each Ball has a volume. This method returns the total volume of
     * all the Balls in the box.
     *
     * @return the volume of the contents of the box.
     */
    public double getVolume() {
        return ballContainer.getVolume();
    }

    /**
     * Returns the number of Balls in this box.
     *
     * @return the number of Balls in this box.
     */
    public int size() {
        return ballContainer.size();
    }

    /**
     * Empties the box, i.e. removes all its contents.
     */
    public void clear() {
        ballContainer.clear();
    }

    /**
     * This method returns <code>true</code> if this box contains
     * the specified Ball. It will return <code>false</code> otherwise.
     *
     * @param b Ball to be checked if its in box
     * @return true if this box contains the specified Ball. Returns
     * false, otherwise.
     * @spec.requires b != null.
     */
    public boolean contains(Ball b) {
        return ballContainer.contains(b);
    }

}
