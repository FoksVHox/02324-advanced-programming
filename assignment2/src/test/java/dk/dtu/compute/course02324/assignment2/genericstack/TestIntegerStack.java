package dk.dtu.compute.course02324.assignment2.genericstack;

import dk.dtu.compute.course02324.assignment2.genericstack.types.Stack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is an abstract test class for all kinds of stack implementations.
 * Sub-classes must create a {@see dk.dtu.compute.course02324.experiments.stack1.Stack}
 * of the specific kind in their {@see #setUp()} method;
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public abstract class TestIntegerStack {

    protected Stack<Integer> stack;

    final int TESTLIMIT = 10000;

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
        Assert.assertThrows(IllegalStateException.class, () -> { stack.pop(); });
    }

    @Test
    public void testTopOnEmptyStack() {
        Assert.assertThrows(IllegalStateException.class, () -> { stack.top(); });
    }

    @Test
    public void testPushEmptyElement() {
        // note that this exception is caused by the @NotNull assertion for the
        // parameter of the push operation!
        Assert.assertThrows(IllegalArgumentException.class, () -> { stack.push(null); });
    }


}