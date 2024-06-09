package pt.ubi;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;


/**
 * FXML Controller class
 *
 * @author jpc
 */
public class input {
    @FXML
    private TextArea txtVertA;
    @FXML
    private TextArea txtVertB;
    
    public int mode;
    
    Graph<String, DefaultWeightedEdge> graphW;
    Graph<String,DefaultEdge> graphD;
    
    public BFSShortestPath<String, DefaultEdge> bfs;
    
    public void setMode(int mode){
        this.mode = mode;
    }
    
    public void inputGraphs(Graph<String, DefaultWeightedEdge> graphW){
        this.graphW = graphW;
    }
    
    public void inputGraphsD(Graph<String,DefaultEdge> graphD){
        this.graphD = graphD;
    }
    
    public void inputBFS(BFSShortestPath<String, DefaultEdge> bfs){
        this.bfs = bfs;
    }
    
    
    public void nextOutputController(ActionEvent event) throws IOException{
        String s1 = txtVertA.getText(), s2 = txtVertB.getText();
        if(s1.equals("") || s2.equals("")){
            return;
        }
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/algorithm_output.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        
        algorithm_output out = fxmlLoader.getController();
        out.setS1(txtVertA.getText());
        out.setS2(txtVertB.getText());
        //MODE == 0 -> BFS INPUT MODE
        if(mode == 0 && bfs != null){
            out.setTextBFSEdges(bfs);
        }
        else if(mode == 1){
            out.setDijkstra(graphW);
        }else if(mode == 2){
            out.setTextConnectivity(graphD);
        }else if(mode == 3){
            return;
        }else{
            return;
        }
        
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("D:");
        stage.setScene(new Scene(root));
        stage.show();
        
        Stage thisStage = (Stage) txtVertA.getScene().getWindow();
        thisStage.close();
        thisStage = null;
    }
}