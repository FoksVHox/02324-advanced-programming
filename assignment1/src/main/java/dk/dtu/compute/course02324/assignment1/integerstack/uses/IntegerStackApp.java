package dk.dtu.compute.course02324.assignment1.integerstack.uses;

import dk.dtu.compute.course02324.assignment1.integerstack.implementations.LinkedListStack;
import dk.dtu.compute.course02324.assignment1.integerstack.types.Stack;
import dk.dtu.compute.course02324.assignment1.integerstack.implementations.ArrayStack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A JavaFX application with a simple GUI for manually interacting
 * and testing the different implementations of a
 * {@link Stack} of
 * integers.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class IntegerStackApp extends Application {

    /**
     * The stage of the GUI of this test application.
     */
    private Stage stage;

    /**
     * The pane on which the actual interaction with the
     * stack will be added.
     */
    private Pane root;

    /**
     * The GUI for a specific stack.
     */
    private IntegerStackGUI intStackGUI;

    /**
     * The method starting the application, which sets up the GUI
     * elements of this application.
     *
     * @param stage the stage for this application (provided by JavaFX)
     * @throws Exception if something should go wrong (required by super class)
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        MenuBar menuBar = createMenuBar();
        root = new Pane();
        VBox box = new VBox(menuBar, root);
        Scene scene = new Scene(box);

        this.stage.setScene(scene);

        this.stage.setTitle("Integer Stack Test");
        this.stage.setResizable(false);
        this.stage.sizeToScene();
        this.stage.show();
    }

    /**
     * Methods used for changing to a different stack (implementation),
     * and updating the GUI accordingly.
     *
     * @param stack new stack for which GUI should be initialized; it can
     *              be <code>null</code>
     */
    private void switchStack(Stack stack) {
        // if there exists a GUI for some stack already, this GUI
        // is removed.
        if (intStackGUI != null) {
            root.getChildren().remove(intStackGUI);
            // alternatively, we could just remove all children from root:
            // root.getChildren().clear();
        }

        // if a stack is provided, a corresponding GUI is created,
        // attached to the stack and placed on the GUI's root element.
        if (stack != null) {
            intStackGUI = new IntegerStackGUI(stack);
            root.getChildren().add(intStackGUI);
            this.stage.setTitle("Integer Stack Test: " + stack.getClass().getSimpleName());
        } else {
            // otherwise only the title of the application window is changed
            intStackGUI = null;
            this.stage.setTitle("Integer Stack Test: no implementation");
        }

        // since this application is not resizable by the user, the application
        // needs to adjust the size of the GUI to the new content, when the
        // GUI arrangement changes.
        stage.sizeToScene();
    }

    /**
     * Methods for creating the menu bar of the application. This menu bar
     * as a single menu, where the user can change the stack implementation
     * to be tested.
     *
     * @return
     */
    private MenuBar createMenuBar() {
        // The menu for the choosing an ammplication
        Menu selectMenu = new Menu("Choose Impl");

        // The individual menu items for the different choices, with the
        // respective actions creating the respective stack.
        MenuItem itemArrayStackImpl = new MenuItem("Array Implementation");
        itemArrayStackImpl.setOnAction(
                e -> {
                    Stack stack = new ArrayStack();
                    switchStack(stack);
                }
        );
        selectMenu.getItems().add(itemArrayStackImpl);

        MenuItem itemLinkedListStackImpl = new MenuItem("Linked List Implementation");
        itemLinkedListStackImpl.setOnAction(
                e -> {
                    Stack stack = new LinkedListStack();
                    switchStack(stack);
                }
        );
        selectMenu.getItems().add(itemLinkedListStackImpl);

        MenuItem noStackImpl = new MenuItem("No Implementation");
        noStackImpl.setOnAction(
                e -> {
                    switchStack(null);
                }
        );
        selectMenu.getItems().add(noStackImpl);

        // creating the menu bar with the single menu on it
        MenuBar menubar = new MenuBar();
        menubar.setMinWidth(400);
        menubar.getMenus().add(selectMenu);

        return menubar;
    }

    /**
     * The main method used to start the JavaFX application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
