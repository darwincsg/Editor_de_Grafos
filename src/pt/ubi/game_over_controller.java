package pt.ubi;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class game_over_controller {
    
    @FXML
    public Label win;

    public void change_text(String s) {
        win.setText(s);
    }
}
