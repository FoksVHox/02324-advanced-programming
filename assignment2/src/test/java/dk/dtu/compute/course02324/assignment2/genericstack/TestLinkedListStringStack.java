package dk.dtu.compute.course02324.assignment2.genericstack;

import dk.dtu.compute.course02324.assignment2.genericstack.implementations.LinkedListStack;

/**
 * Conrete test class for testing the LinkedListStack.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class TestLinkedListStringStack extends TestStringStack {

    @Override
    public void setUp() throws Exception {
        stack = new LinkedListStack<String>();
    }

}
