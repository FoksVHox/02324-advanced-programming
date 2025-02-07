package dk.dtu.compute.course02324.assignment1.integerstack;

import dk.dtu.compute.course02324.assignment1.integerstack.implementations.LinkedListStack;

/**
 * Concrete test class for testing the LinkedListStack.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class TestLinkedListStack extends TestStack {

    @Override
    public void setUp() throws Exception {
        stack = new LinkedListStack();
    }

}
