package performative;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Performative performative;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/kinglaugh.png"));
    private Image performativeImage = new Image(this.getClass().getResourceAsStream("/images/kingsad.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Performative instance */
    public void setPerformative(Performative p) {
        performative = p;
        // Add initial greeting message when Performative is set
        showGreeting();
    }

    /**
     * Displays the initial greeting message from Performative.
     */
    private void showGreeting() {
        String greetingMessage = performative.getWelcomeMessage();
        dialogContainer.getChildren().add(
                DialogBox.getPerformativeDialog(greetingMessage, performativeImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Performative's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = performative.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPerformativeDialog(response, performativeImage)
        );
        userInput.clear();
    }
}
