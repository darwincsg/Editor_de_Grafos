package pt.ubi;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class
 *
 * @author jpc
 */
public class inputWeight {
    
    private String inputValue;

    @FXML
    private TextField inputField;
    @FXML
    private Button confirmButton;

    public void show(Stage stage) {
        VBox vbox = new VBox();
        inputField = new TextField();
        confirmButton = new Button("Confirmar");

        confirmButton.setOnAction(e -> {
            inputValue = inputField.getText();
            stage.close(); // Cerrar la ventana
        });

        vbox.getChildren().addAll(inputField, confirmButton);
        Scene scene = new Scene(vbox, 200, 100);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); // Hacer la ventana modal
    }

    public String getInputValue() {
        return inputValue;
    }
    
}