package dk.dtu.compute.course02324.assignment1.integerstack;

import dk.dtu.compute.course02324.assignment1.integerstack.types.Stack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is an abstract test class for all kinds of stack implementations.
 * Sub-classes must create an instance of a specific implementation of a
 * {@link dk.dtu.compute.course02324.assignment1.integerstack.types.Stack}
 * in their {@link #setUp()} method for the implementation to be tested,
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public abstract class TestStack {

    protected Stack stack;

    final int TESTLIMIT = 10;

    @Before
    abstract public void setUp() throws Exception;

    @Test
    public void testStackEmptyOnCreation() {
        Assert.assertTrue("Stack is not empty initially", stack.isEmpty());
    }

    @Test
    public void testPushPopSequence() {
        for (int i = 0; i <= TESTLIMIT; i++) {
            stack.push(i);
            int value = stack.top();
            Assert.assertEquals(
                    "Pushed value is not on top",
                    i,
                    value);
        }

        for (int i = TESTLIMIT; i >= 0; i--) {
            int value = stack.pop();
            Assert.assertEquals(
                    "Popped wrong value from stack",
                    i,
                    value);
        }

        Assert.assertTrue(
                "Stack is not empty finally",
                stack.isEmpty());
    }

    @Test
    public void testPopOnEmptyStack() {
        Assert.assertNull("Pop on empty stack not null", stack.pop());
    }


}