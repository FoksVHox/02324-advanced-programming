package dk.dtu.compute.course02324.assignment2.genericstack.types;

/**
 * A stack of elements of some types <code>E</code>. An arbitrary number
 * of elements of that type can be added (pushed) to the stack. The
 * elements can be obtained (popped) from the stack in a last in
 * first out fashion.
 *
 * This is a generic type, where the type of the elements is defined by
 * the type parameter <code>E</code>, which is provided when defining
 * variables and when instantiating new Stacks of that type.
 *
 * In addition, this interface defines exceptions for situations
 * in which the stack operations are used in a wrong way.
 *
 * @param <E> the type of the values/elements on the stack
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public interface Stack<E> {

    /**
     * Removes all elements from the stack. The stack will be empty after the
     * call returns.
     */
    void clear();

    /**
     * Removes the top element from the stack and returns the that
     * element. If the stack is empty, the stack is not changed, but
     * throws a {@link IllegalStateException}.
     *
     * @return the top element of the stack
     * @throws IllegalStateException when the stack is empty
     */
    E pop() throws IllegalStateException;

    /**
     * Returns the top element of the stack. If the stack is empty,
     * the stack is not changed, and a {@link IllegalStateException}
     * is thrown.
     *
     * @return the value of the top element of the stack
     * @throws IllegalStateException when the stack is empty
     */
    E top() throws IllegalStateException;

    /**
     * Adds the specified value as the top element of the stack.
     *
     * @param value the value added to the stack
     * @throws IllegalArgumentException if the provided value is null
     */
    void push(E value) throws IllegalArgumentException;

    /**
     * Returns <code>true</code> if the stack does not contain any elements.
     *
     * @return <code>true</code> if the stack contains no elements
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of elements on the stack. This is called the size
     * of the stack.
     *
     * @return the current size of the stack
     */
    int size();

}