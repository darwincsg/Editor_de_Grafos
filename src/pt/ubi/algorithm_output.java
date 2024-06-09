package pt.ubi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultEdge;


/**
 * FXML Controller class
 *
 * @author jpc
 */
public class algorithm_output {
    
    @FXML
    private TextArea txtEdges;
    
    private String s1;
    private String s2;

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }
    
    public void setTextBFSEdges(BFSShortestPath<String, DefaultEdge> bfs){
        try{
            txtEdges.setText(bfs.getPath(s1, s2).getVertexList().toString());
        }catch(RuntimeException e){
            txtEdges.setText("Theres no way");
        }
    }
    
    ////DFS CYCLE 
    public void setTextDFSCycle(Graph<String, DefaultEdge> graph){
        List<String> cycle = findCycle(graph);
        if (cycle != null) {
            txtEdges.setText("Has cycle on: " + cycle.toString());
            return;
        }
        txtEdges.setText("Has not cycle");
    }
    
    public static List<String> findCycle(Graph<String, DefaultEdge> graph) {
        Set<String> visited = new HashSet<>();
        Map<String, String> parentMap = new HashMap<>();
        for (String vertex : graph.vertexSet()) {
            if (!visited.contains(vertex)) {
                List<String> cycle = dfs(vertex, null, visited, parentMap, graph);
                if (cycle != null) {
                    return cycle;
                }
            }
        }
        return null;
    }

    private static List<String> dfs(String current, String parent, Set<String> visited, Map<String, String> parentMap, Graph<String, DefaultEdge> graph) {
        visited.add(current);
        parentMap.put(current, parent);
        for (DefaultEdge edge : graph.edgesOf(current)) {
            String neighbor = graph.getEdgeTarget(edge).equals(current) ? graph.getEdgeSource(edge) : graph.getEdgeTarget(edge);
            if (!neighbor.equals(parent)) {
                if (visited.contains(neighbor)) {
                    // Ciclo detectado, construir el ciclo
                    List<String> cycle = new ArrayList<>();
                    cycle.add(neighbor);
                    String step = current;
                    while (!step.equals(neighbor)) {
                        cycle.add(step);
                        step = parentMap.get(step);
                    }
                    cycle.add(neighbor);
                    Collections.reverse(cycle);
                    return cycle;
                } else {
                    List<String> cycle = dfs(neighbor, current, visited, parentMap, graph);
                    if (cycle != null) {
                        return cycle;
                    }
                }
            }
        }
        return null;
    }
    //// END
    
    public void nextController(ActionEvent event) throws IOException{
        Stage thisStage = (Stage) txtEdges.getScene().getWindow();
        thisStage.close();
        thisStage = null;
    }
}