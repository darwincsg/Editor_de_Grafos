package pt.ubi;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;

/**
 * FXML Controller class
 *
 * @author jpc
 */
public class View1Controller implements Initializable {
    
    @FXML private TextField txtfA;
    @FXML private TextField txtfB;
    @FXML private TextField txtfC;
    
    @FXML private Label lblPoints;
    
    int points;
    
    private Integer str2int(String s) {
        try {
            Integer n = Integer.parseInt(s);
            return n;
        }
        catch (Exception e) {
            return 0;
         }
    }
     public void receivePoints(int n) {
        points += n;
        lblPoints.setText(points+" points");
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Random r= new Random();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int width = (int) screenBounds.getWidth();
        int heigh = (int) screenBounds.getHeight();
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLView2.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Resultado");
            //stage.setX(heigh);
            stage.setScene(new Scene(root));
            //stage.setX(r.nextInt(width-(int)stage.getWidth()));
            //stage.setY(r.nextInt(heigh-(int)stage.getHeight()));
            stage.show();
             
            View2Controller controller2 = fxmlLoader.getController();
            controller2.receiveNumbers(
                    str2int(txtfA.getText()), 
                    str2int(txtfB.getText()), 
                    str2int(txtfC.getText()), 
                    points
            );
            Stage thisStage = (Stage) txtfA.getScene().getWindow();
            thisStage.close();
            thisStage = null; //==> libertar memória.
        }
        catch (Exception e) {}
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        points = 0;
        Random r= new Random();
        txtfA.setText(""+r.nextInt(100));
        txtfB.setText(""+r.nextInt(100));
        txtfC.setText(""+r.nextInt(100));
    
    }    
    
}