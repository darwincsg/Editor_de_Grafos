package pt.ubi;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author jpc
 */
public class output {
    @FXML
    private TextArea txtVertices;
    @FXML
    private TextArea txtEdges;
    
    
    public void setTextVertices(String s){
        txtVertices.setText(s);
    }
    
    public void setTextEdges(String s){
        txtEdges.setText(s);
    }
    
    
    public void nextController(ActionEvent event) throws IOException{
        Stage thisStage = (Stage) txtVertices.getScene().getWindow();
        thisStage.close();
        thisStage = null;
    }
}