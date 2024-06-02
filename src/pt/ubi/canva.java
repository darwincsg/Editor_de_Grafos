package pt.ubi;

import java.awt.Point;
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

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;


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
            
    private int id = 0;
    
    Vertice[] connection = new Vertice[2];
    
    private List<Vertice> verticeV = new ArrayList<>();
    
    Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    
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
        for(Vertice point : verticeV){
            context.fillOval(point.getLocation().x ,point.getLocation().y,25,25); // (coordX,coordY,Weight, Height)
        }
    }
    
    private void addController(Point point){
        String name = setNames();
        verticeV.add(new Vertice(point,name));
        graph.addVertex(name);
    }
    
    private void removeController(Point point){
        Vertice removePoint = iteratorPointList(point);
        if(removePoint == null){
            return;
        }
        verticeV.remove(removePoint);
    }
    
    private void connectController(GraphicsContext context){
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);
        context.setLineDashes(0);
        context.strokeLine(connection[0].getLocation().x + 12.5, connection[0].getLocation().y + 12.5, connection[1].getLocation().x + 12.5, connection[1].getLocation().y + 12.5);
         
        graph.addEdge(connection[0].getName(), connection[1].getName());
    }
    
    public void mouseHandler(MouseEvent event){
        GraphicsContext context;
        context = canva.getGraphicsContext2D();
        
        int clickX = (int) event.getX();
        int clickY = (int) event.getY();
        
        Point point = new Point(clickX,clickY);
        if(stateC){
            if(connectAux){
                Vertice connectPoint = iteratorPointList(point);
                if(connectPoint == null){
                    return;
                }
                connection[1] = connectPoint;
                connectController(context);
                connectAux = false;
            }else{
                Vertice connectPoint = iteratorPointList(point);
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
    public Vertice iteratorPointList(Point point){
        for(Vertice points : verticeV){
                boolean pointConf = ClickConfirm(point,points.getLocation(),25);
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
    
    private String setNames(){
        return "v" + id++;
    }
    
    public void printGrahp(){
        System.out.println("Vertices: " + graph.vertexSet());
        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
            System.out.println("Arista: " + edge + ", desde " + source + " hasta " + target);
        }
    }
    
}