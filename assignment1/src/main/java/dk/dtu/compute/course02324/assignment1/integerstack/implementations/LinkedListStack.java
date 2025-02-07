package dk.dtu.compute.course02324.assignment1.integerstack.implementations;

import dk.dtu.compute.course02324.assignment1.integerstack.types.Stack;

/**
 * Implements a {@link Stack} of integers as a (singly) linked list.
 * TODO note that this is template only, and the actual functions of
 *      the stack are not implemented yet. Impelemnting these methods
 *      is the task of assignment 1.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class LinkedListStack implements Stack {

    private StackElement top = null;

    private int size = 0;

    @Override
    public void clear() {
        // TODO must be implemented
    }

    @Override
    public Integer pop() {
        if (size == 0) return null;

        Integer poppedValue = top.value;
        top = top.next;
        size--;

        return poppedValue;
    }

    @Override
    public Integer top() {
        return this.top.value;
    }

    @Override
    public void push(Integer value) {
        if(size == 0) {
            StackElement el = new StackElement(value, null);
            top = el;

            size++;
            return;
        }

        StackElement el = new StackElement(value, top);
        top = el;
        size++;
    }

    @Override
    public int size() {
        return this.size;
    }


    /**
     * Represents a single element on the linked list stack with its
     * value and a pointer to the next element.
     *
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    private class StackElement {

        private Integer value;

        private StackElement next;

        private StackElement(Integer value, StackElement next) {
            this.value = value;
            this.next = next;
        }
    }

}
