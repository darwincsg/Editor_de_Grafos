package pt.ubi;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;


/**
 * FXML Controller class
 *
 * @author jpc
 */
public class canvaDirectW {
    @FXML
    public Canvas canva;

    private boolean state1 = false;
    private boolean stateC = false;
    private boolean connectAux = false; 
            
    private int id = 0;
    
    Vertice[] connection = new Vertice[2];
    
    private List<Vertice> verticeV = new ArrayList<>();
    
    Graph<String, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

    
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
        context.setFont(new Font("Arial", 15));
        context.setFill(Color.BLACK);
        for(Vertice point : verticeV){
            context.fillOval(point.getLocation().x ,point.getLocation().y,25,25); // (coordX,coordY,Weight, Height)
            context.fillText(point.getName(), point.getLocation().x + 5, point.getLocation().y + 40);
        }
        
        for (DefaultWeightedEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
            double weight = graph.getEdgeWeight(edge);
            int pointX, pointY;
            Vertice x = null,y = null;
            for(Vertice v : verticeV){
                if(source.equals(v.getName())){
                    x = v;
                }
                if(target.equals(v.getName())){
                    y = v;
                }   
            }
            pointX = (x.getLocation().x + y.getLocation().x)/2;
            pointY = (x.getLocation().y + y.getLocation().y)/2;
            context.setStroke(Color.BLACK);
            context.setLineWidth(2);
            context.setLineDashes(0);
            context.strokeLine(x.getLocation().x + 12.5, x.getLocation().y + 12.5, y.getLocation().x + 12.5, y.getLocation().y + 12.5);
            context.fillText(String.valueOf(weight), pointX, pointY);
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
        graph.removeVertex(removePoint.getName());
    }
    
    private void connectController(GraphicsContext context){
        String Winput;
        int Weight;
        
        Stage inputStage = new Stage();
        inputStage.setTitle("Weight de Input");
        
        inputWeight in = new inputWeight();
        in.show(inputStage);
        inputStage.showAndWait();
        
        Winput = in.getInputValue();
        
        try{
            Weight =  Integer.parseInt(Winput);
        }
        catch(NumberFormatException e){
            Weight = 1;
        }
        
        graph.setEdgeWeight((graph.addEdge(connection[0].getName(), connection[1].getName())), Weight);
        paintController(context);
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
    
    public void printDijkstra() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/input.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle(":D");
        stage.setScene(new Scene(root));
        stage.show();
        input in = fxmlLoader.getController();
        in.inputGraphs(graph);
        in.setMode(1);
    }
    public void printFloydWarshall() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/algorithm_output.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        
        algorithm_output out = fxmlLoader.getController();
        out.setTextFloydWarshall(graph);
        
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("D:");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    public void printPRIM() throws IOException{
        PrimMinimumSpanningTree<String, DefaultWeightedEdge> primMST = new PrimMinimumSpanningTree<>(graph);
        Set<DefaultWeightedEdge> mstEdges = primMST.getSpanningTree().getEdges();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/algorithm_output.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        
        algorithm_output out = fxmlLoader.getController();
        out.setTextPRIM(mstEdges, graph);
        
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("D:");
        stage.setScene(new Scene(root));
        stage.show();
        
    }
    
    public void printGrahp() throws IOException{
        Set<String> s = graph.vertexSet();        
        String edges = "";
        for (DefaultWeightedEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
            double weight = graph.getEdgeWeight(edge);
            edges = edges + ("|" + source + " --(" + weight + ")--> " + target + '\n');
        }
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/output.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle(":D");
        stage.setScene(new Scene(root));
        stage.show();
        
        output out = fxmlLoader.getController();
        out.setTextVertices(s.toString());
        out.setTextEdges(edges);
    }
    
}