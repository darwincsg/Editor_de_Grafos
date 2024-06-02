package pt.ubi;

import java.awt.Point;
import javafx.application.Application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author jpc
 */
public class canva {
    @FXML
    public Canvas canva;

    private boolean state1 = false;
    private boolean stateC = false;
    private boolean connectAux = false; 
    private boolean SimpleGraph = false;
    private boolean SimpleWeightedGraph = false;
    private boolean SimpleDirectedGraph = false;
    private boolean SimpleDirectedWeightedGraph = false;
            
    Point[] connection = new Point[2];
    
    private List<Point> vertices = new ArrayList<>();
    
    public void setSimpleGraph(boolean SimpleGraph) {
        this.SimpleGraph = SimpleGraph;
    }

    public void setSimpleWeightedGraph(boolean SimpleWeightedGraph) {
        this.SimpleWeightedGraph = SimpleWeightedGraph;
    }

    public void setSimpleDirectedGraph(boolean SimpleDirectedGraph) {
        this.SimpleDirectedGraph = SimpleDirectedGraph;
    }

    public void setSimpleDirectedWeightedGraph(boolean SimpleDirectedWeightedGraph) {
        this.SimpleDirectedWeightedGraph = SimpleDirectedWeightedGraph;
    }
    
    public void addButtonController(ActionEvent event){
        state1 = true;
        stateC = false;
    }
    
    public void removeButtonController(ActionEvent event){
        state1 = false;
        stateC = false;
    }
    
    public void connectButtonController(ActionEvent event){
        stateC = true;
    }
        
    public void paintController(GraphicsContext context){        
        context.clearRect(0, 0, canva.getWidth(), canva.getHeight());
        for(Point point : vertices){
            context.fillOval(point.x ,point.y,25,25); // (coordX,coordY,Weight, Height)
        }
    }
    
    private void addController(Point point){
        vertices.add(point);
    }
    
    private void removeController(Point point){
        Point removePoint = iteratorPointList(point);
        if(removePoint == null){
            return;
        }
        vertices.remove(removePoint);
    }
    
    private void connectController(GraphicsContext context){
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);
        context.setLineDashes(0);
        context.strokeLine(connection[0].x + 12.5, connection[0].y + 12.5, connection[1].x + 12.5, connection[1].y + 12.5);
    }
    
    public void mouseHandler(MouseEvent event){
        GraphicsContext context;
        context = canva.getGraphicsContext2D();
        
        int clickX = (int) event.getX();
        int clickY = (int) event.getY();
        
        Point point = new Point(clickX,clickY);
        if(stateC){
            if(connectAux){
                Point connectPoint = iteratorPointList(point);
                if(connectPoint == null){
                    return;
                }
                connection[1] = connectPoint;
                connectController(context);   
                connectAux = false;
            }else{
                Point connectPoint = iteratorPointList(point);
                if(connectPoint == null){
                    return;
                }
                connection[0] = connectPoint;
                connectAux = true;
            }
        }else{
            if(state1){
                addController(point);
                paintController(context);
            }else{
                removeController(point);
                paintController(context);
            }
        }
        
    }
    
    
    public void nextHandler() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("canva.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Resultado");
        stage.setScene(new Scene(root));
        stage.show();
        
        
    }
    public Point iteratorPointList(Point point){
        for(Point points : vertices){
                boolean pointConf = ClickConfirm(point,points,25);
                if(pointConf){
                    return points;
                }
        }
        return null;
    }
    
    public boolean ClickConfirm(Point clickLocation, Point location, int size){
        int RangeX = location.x + size;
        int RangeY = location.y + size;
        
        if(clickLocation.x <= RangeX && clickLocation.x >= location.x){
            if(clickLocation.y <= RangeY && clickLocation.y >= location.y){
                return true;
            }
        }        
        return false;
    }
    
}