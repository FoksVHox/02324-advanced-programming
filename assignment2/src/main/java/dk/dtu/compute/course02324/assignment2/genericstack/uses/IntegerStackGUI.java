package dk.dtu.compute.course02324.assignment2.genericstack.uses;

import dk.dtu.compute.course02324.assignment2.genericstack.types.Stack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.validation.constraints.NotNull;
import java.io.PrintWriter;
import java.io.StringWriter;

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
    final private Stack<Integer> stack;

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
     * Show stack trace in exception textarea?
     */
    private boolean stackTrace = false;

    /**
     * Text area in which the exceptions are shown.
     */
    private TextArea textAreaExceptions;

    /**
     * Constructor which sets up the GUI attached to the stack.
     *
     * @param stack the stack which the GUI component interacts with;
     *              must not be <code>null</code>
     */
    public IntegerStackGUI(@NotNull Stack<Integer> stack) {
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
                    if (newValue.isEmpty()) {
                        field.setText("0");
                    } else {
                        field.setText(oldValue);
                    }
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
                try {
                    lastPopped = stack.pop();
                } catch (IllegalStateException ex) {
                    if (stackTrace) {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        ex.printStackTrace(pw);
                        textAreaExceptions.appendText(sw.toString()+"\n");
                    } else {

                        textAreaExceptions.appendText(ex.getMessage()+"\n");
                    }

                } finally {
                    update();
                }
            });

        // button for clearing the stack
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(
                e -> {
                    stack.clear();
                    textAreaExceptions.clear();
                    // makes sure that the GUI is updated accordingly
                    update();
                });

        CheckBox checkBox = new CheckBox("Debug mode");
        checkBox.setOnAction(e -> stackTrace = checkBox.isSelected());

        // combines the above elements into two vertically arranged boxes
        // which are then added to the grid pane.
        VBox actionBox = new VBox(field, pushButton, popButton, clearButton,checkBox);
        actionBox.setSpacing(5.0);
        this.add(actionBox, 0, 0);

        labelTop = new Label("");
        labelSize = new Label("");
        labelLastPopped = new Label("");

        Label labelExceptions = new Label("Exceptions:");
        textAreaExceptions = new TextArea();
        textAreaExceptions.setWrapText(false);
        textAreaExceptions.setText("");
        textAreaExceptions.setEditable(false);
        textAreaExceptions.setScrollTop(Double.MIN_VALUE);

        ScrollPane scrollPane = new ScrollPane(textAreaExceptions);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox stackStateBox = new VBox(labelTop, labelSize, labelLastPopped, labelExceptions, scrollPane);
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
        labelSize.setText("size: " + stack.size());

        try {
            Integer top = stack.top();
            if (top == null) {
                labelTop.setText("top: <none>");
            } else {
                labelTop.setText("top: " + top);
            }
        } catch (Exception e) {
            labelTop.setText("top: <none>");
        }

        if (lastPopped == null) {
            labelLastPopped.setText("last popped: <none>       ");
        } else {
            labelLastPopped.setText("last popped: " + lastPopped);
        }
    }

}
