package dk.dtu.compute.course02324.assignment3.lists.uses;

import dk.dtu.compute.course02324.assignment3.lists.implementations.BubbleSort;
import dk.dtu.compute.course02324.assignment3.lists.implementations.GenericComparator;
import dk.dtu.compute.course02324.assignment3.lists.types.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A GUI element that is allows the user to interact and
 * change a list of persons.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class PersonsGUI extends GridPane {

    /**
     * The list of persons to be maintained in this GUI.
     */
    final private List<Person> persons;

    private GridPane personsPane;

    private int weightCount = 1;

    private Label avgWeightLabel;
    private Label mostFrequentNameLabel;

    /**
     * Constructor which sets up the GUI attached a list of persons.
     *
     * @param persons the list of persons which is to be maintained in
     *                this GUI component; it must not be <code>null</code>
     */
    public PersonsGUI(@NotNull List<Person> persons) {
        this.persons = persons;
        this.setVgap(5.0);
        this.setHgap(5.0);

        // text filed for user entering a name
        TextField field = new TextField();
        field.setPrefColumnCount(5);
        field.setText("enter name");

        TextField weightField = new TextField();
        weightField.setPrefColumnCount(5);
        weightField.setText("enter weight");

        TextField indexField = new TextField();
        indexField.setPrefColumnCount(5);
        indexField.setPromptText("Enter index");

        avgWeightLabel = new Label("Average Weight: -");
        mostFrequentNameLabel = new Label("Most Frequent Name: -");

        // button for adding a new person to the list (based on
        // the name in the text field (the weight is just incrementing)
        Button addButton = new Button("Add");

        addButton.setOnAction(e -> {
            try {
                String name = field.getText();
                int weight = Integer.parseInt(weightField.getText());
                persons.add(new Person(name, weight));
                update();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

        Button addAtIndexButton = new Button("Add at Index");
        addAtIndexButton.setOnAction(e -> {
            try {
                String name = field.getText();
                double weight = Double.parseDouble(weightField.getText());
                int index = Integer.parseInt(indexField.getText());

                if (index < 0 || index > persons.size()) {
                    throw new IndexOutOfBoundsException("Invalid index: " + index);
                }

                persons.add(index, new Person(name, weight));
                update();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Comparator<Person> comparator = new GenericComparator<>();

        // button for sorting the list (according to the order of Persons,
        // which implement the interface Comparable, which is converted
        // to a Comparator by the GenericComparator above)
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(e -> {
            try {
                BubbleSort.sort(Comparator.comparingDouble(p -> p.weight), persons); // Sort by weight
                update();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // button for clearing the list
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> {
            try {
                persons.clear();
                update();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        });

        // combines the above elements into vertically arranged boxes
        // which are then added to the left column of the grid pane
        VBox actionBox = new VBox(field,weightField, addButton, indexField, addAtIndexButton, sortButton, clearButton);
        actionBox.setSpacing(5.0);
        this.add(actionBox, 0, 0);

        // create the elements of the right column of the GUI
        // (scrollable person list) ...
        Label labelPersonsList = new Label("Persons:");

        personsPane = new GridPane();
        personsPane.setPadding(new Insets(5));
        personsPane.setHgap(5);
        personsPane.setVgap(5);

        ScrollPane scrollPane = new ScrollPane(personsPane);
        scrollPane.setMinWidth(300);
        scrollPane.setMaxWidth(300);
        scrollPane.setMinHeight(300);
        scrollPane.setMaxHeight(300);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // ... and adds these elements to the right-hand columns of
        // the grid pane
        VBox personsList = new VBox(labelPersonsList, scrollPane, avgWeightLabel, mostFrequentNameLabel);
        personsList.setSpacing(5.0);
        this.add(personsList, 1, 0);

        // updates the values of the different components with the values from
        // the stack
        update();
    }

    /**
     * Updates the values of the GUI elements with the current values
     * from the list.
     */
    private void update() {
        personsPane.getChildren().clear();
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            Label personLabel = new Label(i + ": " + person.toString());
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                try {
                    persons.remove(person);
                    update();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            HBox entry = new HBox(deleteButton, personLabel);
            entry.setSpacing(5.0);
            entry.setAlignment(Pos.BASELINE_LEFT);
            personsPane.add(entry, 0, i);
        }
        updateStatistics();
    }

    private void updateStatistics() {
        if (persons.size() == 0) {
            avgWeightLabel.setText("Average Weight: -");
            mostFrequentNameLabel.setText("Most Frequent Name: -");
            return;
        }

        double totalWeight = 0;
        Map<String, Integer> nameCounts = new HashMap<>();

        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            totalWeight += p.weight;
            nameCounts.put(p.name, nameCounts.getOrDefault(p.name, 0) + 1);
        }

        double avgWeight = totalWeight / persons.size();
        avgWeightLabel.setText(String.format("Average Weight: %.2f kg", avgWeight));

        String mostFrequent = Collections.max(nameCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        mostFrequentNameLabel.setText("Most Frequent Name: " + mostFrequent);
    }
}