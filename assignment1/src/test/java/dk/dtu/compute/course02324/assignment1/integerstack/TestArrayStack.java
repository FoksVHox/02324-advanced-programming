package dk.dtu.compute.course02324.assignment1.integerstack;

import dk.dtu.compute.course02324.assignment1.integerstack.implementations.ArrayStack;

/**
 * Concrete test class for testing the ArrayStack.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class TestArrayStack extends TestStack {

    @Override
    public void setUp() throws Exception {
        stack = new ArrayStack();
    }

}
