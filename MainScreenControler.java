import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.scene.control.ColorPicker;

public class MainScreenController {

    // UI elements defined in the FXML file
    @FXML
    private Button addStudent;

    @FXML
    private Rectangle box1;
    @FXML
    private Rectangle box2;
    @FXML
    private Rectangle box3;
    @FXML
    private Rectangle box4;
    @FXML
    private Rectangle box5;
    @FXML
    private Rectangle box6;
    @FXML
    private Rectangle box7;
    @FXML
    private Rectangle box8;
    @FXML
    private Rectangle box9;

    @FXML
    private Label error;

    @FXML
    private Label name1;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private Label name4;
    @FXML
    private Label name5;
    @FXML
    private Label name6;
    @FXML
    private Label name7;
    @FXML
    private Label name8;
    @FXML
    private Label name9;

    @FXML
    private TextField sName;

    @FXML
    private ColorPicker pColor;

    // Initializes the UI elements when the application starts
    @FXML
    public void initialize() {
        // Set the initial color to white
        pColor.setValue(Color.WHITE);
        // Clear any previous error messages
        error.setText("");
    }

    // Handles the "Add Student" button click event
    @FXML
    public void onAddStudentClick(ActionEvent event) {
        // Get the entered student name
        String studentName = sName.getText();

        // Lists to hold labels and desk boxes
        ArrayList<Label> studentNameLabels = new ArrayList<>();
        ArrayList<Rectangle> studentDeskBoxes = new ArrayList<>();

        // Sets to keep track of assigned desks, names, and used colors
        Set<Color> assignedDesks = new HashSet<>();
        Set<Label> assignedNames = new HashSet<>();
        Set<Color> usedColors = new HashSet<>();

        // Add labels and desk boxes to their respective lists
        studentNameLabels.add(name1);
        studentNameLabels.add(name2);
        studentNameLabels.add(name3);
        studentNameLabels.add(name4);
        studentNameLabels.add(name5);
        studentNameLabels.add(name6);
        studentNameLabels.add(name7);
        studentNameLabels.add(name8);
        studentNameLabels.add(name9);


        studentDeskBoxes.add(box1);
        studentDeskBoxes.add(box2);
        studentDeskBoxes.add(box3);
        studentDeskBoxes.add(box4);
        studentDeskBoxes.add(box5);
        studentDeskBoxes.add(box6);
        studentDeskBoxes.add(box7);
        studentDeskBoxes.add(box8);
        studentDeskBoxes.add(box9);

        // Get the selected color from the color picker
        Color selectedColor = pColor.getValue();
        // Check if a color is selected
        if (selectedColor == null) {
            error.setText("ERROR: You did not choose any color");
            return;
        }

        // Validate the entered student name
        if (studentName.trim().isEmpty()) {
            error.setText("ERROR: Student name field cannot be blank");
            return;
        } else if (studentName.trim().length() < 3) {
            error.setText("ERROR: Student name must be at least three characters");
            return;
        }

        // Check if the student name already exists
        for (int i = 0; i < studentNameLabels.size(); i++) {
            error.setText("");
            if (studentNameLabels.get(i).getText().equals(studentName)) {
                error.setText("ERROR: Student name '" + studentName + "' already exists.");
                return;
            }
        }

        // Iterate through student name labels and desk boxes
        for (int i = 0; i < studentNameLabels.size(); i++) {
            if (studentNameLabels.get(i).getText().isEmpty()) {
                // If a label is empty, mark it as an available name and add the associated desk color to usedColors
                assignedNames.add(studentNameLabels.get(i));
                usedColors.add((Color) studentDeskBoxes.get(i).getFill());
            } else if (studentNameLabels.get(i).getText().equals(studentName)) {
                return; // Student name already assigned
            } else {
                // If the label is assigned, add its desk color to assignedDesks and usedColors
                assignedDesks.add((Color) studentDeskBoxes.get(i).getFill());
                usedColors.add((Color) studentDeskBoxes.get(i).getFill());
            }
        }

        // Handle assignment of desk and name
        if (!assignedNames.isEmpty()) {
            if (usedColors.contains(selectedColor)) {
                error.setText("ERROR: White can not be chosen");
                return;
            }
            Random random = new Random();
            Label randomLabel = assignedNames.stream().skip(random.nextInt(assignedNames.size())).findFirst().orElse(null);
            if (randomLabel != null) {
                randomLabel.setText(studentName);
                assignedNames.add(randomLabel);
            }
            int assignedLabelIndex = studentNameLabels.indexOf(randomLabel);
            Rectangle emptyDesk = studentDeskBoxes.get(assignedLabelIndex);
            emptyDesk.setFill(selectedColor);
            assignedDesks.add(selectedColor);
        }

        // Count the number of students
        int numStudents = 0;
        for (int i = 0; i < studentNameLabels.size(); i++) {
            if (!studentNameLabels.get(i).getText().isEmpty()) {
                numStudents++;
            }
        }

        // Check if the class is full
        if (numStudents >= 9) {
            error.setText("The class is full!");
            // Change the error text color to green
            error.setTextFill(Color.GREEN);
            // Reset color picker value to white
            pColor.setValue(Color.WHITE);
            return;
        }
    }
}
