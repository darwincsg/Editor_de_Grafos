package pt.ubi;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author jpc
 */
public class menu {
    @FXML
    private Label lbl1;
    
    private boolean dir;
    private boolean weight;
    
    public void setDir(boolean dir) {
        this.dir = dir;
    }

    public void setWeight(boolean weight) {
        this.weight = weight;
    }
    
    public void dirHandler(ActionEvent event){
        Button b = (Button) event.getSource();
        if(b.getText().equals("Directed")){
            setDir(true);
            return;
        }
        setDir(false);
    }  
    
    public void weightHandler(ActionEvent event){
        Button b = (Button) event.getSource();
        if(b.getText().equals("Weight")){
            setWeight(true);
            return;
        }
        setWeight(false);
    }    
    
    public void nextController() throws IOException{
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("canva.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Resultado");
        stage.setScene(new Scene(root));
        stage.show();
        
        canva controller = fxmlLoader.getController();
        controller.setSimpleDirectedGraph(dir && !weight);
        controller.setSimpleDirectedWeightedGraph(dir && weight);
        controller.setSimpleGraph(!(dir || weight));
        controller.setSimpleWeightedGraph(!dir && weight);
        
        Stage thisStage = (Stage) lbl1.getScene().getWindow();
        thisStage.close();
        thisStage = null;
    }
}