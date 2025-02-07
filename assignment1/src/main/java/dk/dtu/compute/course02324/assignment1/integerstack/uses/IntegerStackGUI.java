package dk.dtu.compute.course02324.assignment1.integerstack.uses;

import dk.dtu.compute.course02324.assignment1.integerstack.types.Stack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.validation.constraints.NotNull;

/**
 * A GUI element that is attached to an integer stack, and has
 * buttons and input fields for interacting with this stack. It
 * is based on a JavaFX {@link javafx.scene.layout.GridPane}.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class IntegerStackGUI extends GridPane {

    /**
     * The stack which is tested.
     */
    final private Stack stack;

    /**
     * The last value popped from the stack.
     */
    private Integer lastPopped;

    /**
     * Label showing the current top element of the stack.
     */
    private Label labelTop;

    /**
     * Label showing the current size of the stack.
     */
    private Label labelSize;

    /**
     * Label showing the last element that was popped from the stack.
     */
    private Label labelLastPopped;

    /**
     * Constructor which sets up the GUI attached to the stack.
     *
     * @param stack the stack which the GUI component interacts with;
     *              must not be <code>null</code>
     */
    public IntegerStackGUI(@NotNull Stack stack) {
        this.stack = stack;

        this.setVgap(5.0);
        this.setHgap(5.0);

        TextField field = new TextField();
        field.setPrefColumnCount(5);
        field.setText("0");
        // the following is a simple way to make sure that the user can only
        // enter Integer values to the text field
        field.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                try {
                    Integer.parseInt(newValue);
                    field.setText(newValue);
                } catch (NumberFormatException e) {
                    field.setText(oldValue);
                }
            }

        });

        // button for pushing the current value of the text field to the stack
        Button pushButton = new Button("Push");
        pushButton.setOnAction(
                e -> {
                    int value = Integer.parseInt(field.getText());
                    stack.push(value);
                    // makes sure that the GUI is updated accordingly
                    update();
                });

        // button for popping the top element from the stack
        Button popButton = new Button("Pop");
        popButton.setOnAction(
                e -> {
                    lastPopped = stack.pop();
                    // makes sure that the GUI is updated accordingly
                    update();
                });

        // button for clearing the stack
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(
                e -> {
                    stack.clear();
                    // makes sure that the GUI is updated accordingly
                    update();
                });

        // combines the above elements into two vertically arranged boxes
        // which are then added to the grid pane.
        VBox actionBox = new VBox(field, pushButton, popButton, clearButton);
        actionBox.setSpacing(5.0);
        this.add(actionBox, 0, 0);

        labelTop = new Label("");
        labelSize = new Label("");
        labelLastPopped = new Label("");

        VBox stackStateBox = new VBox(labelTop, labelSize, labelLastPopped);
        stackStateBox.setSpacing(5.0);
        this.add(stackStateBox, 1, 0);

        // updates the values of the different components with the values from
        // the stack
        update();


    }

    /**
     * Updates the values of the GUI elements with the current values
     * from the stack.
     */
    private void update() {
        Integer top = stack.top();
        if (top == null) {
            labelTop.setText("top: <none>");
        } else {
            labelTop.setText("top: " + top);
        }

        labelSize.setText("size: " + stack.size());

        if (lastPopped == null) {
            labelLastPopped.setText("last popped: <none>       ");
        } else {
            labelLastPopped.setText("last popped: " + lastPopped);
        }
    }

}
