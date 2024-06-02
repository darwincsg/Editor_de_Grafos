package pt.ubi;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author jpc
 */
public class View2Controller implements Initializable {
    
    private int NumberX;
    private int NumberY;
    private int NumberZ;
    private int NumberP;
    
    @FXML private Label lblX;
    @FXML private Label lblY;
    @FXML private Label lblZ;
    
    
    public void receiveNumbers(int NumberX, int NumberY, int NumberZ, int NumberP) {
        this.NumberX = NumberX;
        this.NumberY = NumberY;
        this.NumberZ = NumberZ;
        this.NumberP = NumberP;
        
        lblX.setText(String.valueOf(NumberX));
        lblY.setText(String.valueOf(NumberY));
        lblZ.setText(String.valueOf(NumberZ));
        
        return;
    }
    
    public int contarPoint(){
    
        if(NumberX + NumberY == NumberZ){
            return NumberP + 20;
        }
        return NumberP;
        
    }
     
    @FXML
    private void handleButtonAction(javafx.event.ActionEvent event) {
        Random r= new Random();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int width = (int) screenBounds.getWidth();
        int heigh = (int) screenBounds.getHeight();
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLView1.fxml"));
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
             
            View1Controller controller1 = fxmlLoader.getController();
            controller1.receivePoints(NumberP);
            Stage thisStage = (Stage) lblX.getScene().getWindow();
            thisStage.close();
            thisStage = null;
        }
        catch (Exception e) {}
    }
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }    
    
}