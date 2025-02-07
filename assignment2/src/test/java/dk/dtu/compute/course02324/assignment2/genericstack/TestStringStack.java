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
public abstract class TestStringStack {

    protected Stack<String> stack;

    @Before
    abstract public void setUp() throws Exception;

    @Test
    public void testStackEmptyOnCreation() {
        Assert.assertTrue("Stack is not empty initially", stack.isEmpty());
    }

    @Test
    public void testPushPopSequence() {
        String value1 = "Alpha";
        stack.push(value1);
        String value1t = stack.top();
        Assert.assertEquals(
                "Pushed value is not on top",
                value1,
                value1t);

        String value2 = "Beta";
        stack.push(value2);
        String value2t = stack.top();
        Assert.assertEquals(
                "Pushed value is not on top",
                value2,
                value2t);

        String value3 = "Gamma";
        stack.push(value3);
        String value3t = stack.top();
        Assert.assertEquals(
                "Pushed value is not on top",
                value3,
                value3t);

        Assert.assertEquals("Stack size is wrong",
                3,
                stack.size());

        String value3p = stack.pop();
        Assert.assertEquals(
                "Pushed value is not on top",
                value3,
                value3p);

        Assert.assertEquals("Stack size is wrong",
                2,
                stack.size());

        String value2p = stack.pop();
        Assert.assertEquals(
                "Pushed value is not on top",
                value2,
                value2p);

        Assert.assertEquals("Stack size is wrong",
                1,
                stack.size());

        String value1p = stack.pop();
        Assert.assertEquals(
                "Pushed value is not on top",
                value1,
                value1p);

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