package dk.dtu.compute.course02324.assignment2.genericstack.implementations;

import dk.dtu.compute.course02324.assignment2.genericstack.types.Stack;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Implements a {@link Stack} of integers by using a sufficiently large
 * array. Note that the array is dynamically extended by copying it to
 * a larger array, whenever the stack size exceeds the current length
 * of the array.
 *
 * @param <E> the type of the values/elements on the stack
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class ArrayStack<E> implements Stack<E> {

    /**
     * Constant defining the default size of the array when the
     * stack is created. The value can be any (strictly) positive
     * number. Here, we have chosen <code>10</code>, which is also
     * Java's default for some array-based collection implementations.
     */
    final private int DEFAULT_SIZE = 10;

    /**
     * The array in which the elements of the stack are stored.
     * The top of the stack is towards the higher numbers.
     *
     * NB: there is no really nice and elegant way to create
     *     a new instance of an array of a generic type like
     *        new E[DEFAULT_SIZE]
     *     in Java; the below is the nicest we can get and OK
     *     in our context: as long as it is just used internally
     *     in this class (the array as a whole is never returned
     *     to a client). In the long run (see later lectures and
     *     assignments, we recommend to Java's built-in lists
     *     anyway).
     */
    private E[] array = (E[]) new Object[DEFAULT_SIZE];

    /**
     * The current size of the stack. Note that the top-level
     * element is located at <code>size-1</code> in this array.
     */
    private int size = 0;

    @Override
    public void clear() {
        size = 0;
        array = (E[]) new Object[DEFAULT_SIZE];
    }

    @Override
    public E pop() throws IllegalStateException {
        if (size == 0) throw new IllegalStateException("stack is empty");

        if (size == array.length/2) array = Arrays.copyOf(array, array.length/2);

        E poppedValue = top();
        size--;
        return poppedValue;
    }

    @Override
    public E top() {
        if (size == 0) throw new IllegalStateException("stack is empty");
        return array[size - 1];
    }

    @Override
    public void push(E value) {
        if(value == null) throw new IllegalArgumentException("value cannot be null");

        if(size >= array.length) {
            int newCapacity = array.length*2;
            E[] newArray = (E[]) new Object[newCapacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        array[size++] = value;
    }

    @Override
    public int size() {
        return this.size;
    }

}
