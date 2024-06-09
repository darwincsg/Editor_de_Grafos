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
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.cycle.JohnsonSimpleCycles;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;


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
    //SIMPLE WEIGHT
        public void setTextPRIM(Set<DefaultWeightedEdge> mstEdges, Graph<String, DefaultWeightedEdge> graph){
            String edges = "Minimum Spanning Tree:\n";
            for (DefaultWeightedEdge edge : mstEdges) {
                String source = graph.getEdgeSource(edge);
                String target = graph.getEdgeTarget(edge);
                double weight = graph.getEdgeWeight(edge);
                edges = edges + (source + " - " + target + " : " + weight + "\n");
            }
            txtEdges.setText(edges);
        }
        
        public void setDijkstra(Graph<String, DefaultWeightedEdge> graph){
            try{
                DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
                txtEdges.setText(dijkstraAlg.getPath(s1, s2).toString());
            }catch(NullPointerException e){
                txtEdges.setText("THERES NO WAY");
            }catch(IllegalArgumentException e){
                txtEdges.setText("ILEGAL ARGUMENTS");
            }
            
        }
        
        public void setB_Ford(Graph<String, DefaultWeightedEdge> graph, List<Vertice> verticeV){
            BellmanFordShortestPath<String, DefaultWeightedEdge> bellmanFordAlg = new BellmanFordShortestPath<>(graph);
            String edges = "";
            for(Vertice v : verticeV){
                String verticeName = v.getName();
                edges = edges + ("---------------" + verticeName.toUpperCase() + "---------------\n");
                for (String vertex : graph.vertexSet()) {
                    List<DefaultWeightedEdge> path = bellmanFordAlg.getPath(verticeName, vertex).getEdgeList();
                    edges = edges + "Shortest path from " + verticeName.toUpperCase() + " to " + vertex.toUpperCase() + ": " + path + "\n";
                    edges = edges + ("Path weight: " + bellmanFordAlg.getPathWeight(verticeName, vertex) + "\n");
                }
            }
            txtEdges.setText(edges);
        }
    //
    
    //SIMPLE UNWEIGHT
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
    ////
        
    //DIRECT UNWEIGHT
        public void setTextJhonson(Graph<String, DefaultEdge> graph){
            JohnsonSimpleCycles<String, DefaultEdge> johnsonSimpleCycles = new JohnsonSimpleCycles<>(graph);
            List<List<String>> cycles = johnsonSimpleCycles.findSimpleCycles();
            String edges = "";
            for (List<String> cycle : cycles) {
                edges = edges + ("Cycle: " + cycle + "\n");
            }
            txtEdges.setText(edges);
            
            if(cycles.isEmpty()){
                txtEdges.setText("NO CYCLES");
            }
        }
        
        public void setTextConnectivity(Graph<String, DefaultEdge> graph){
            ConnectivityInspector<String, DefaultEdge> connectivityInspector = new ConnectivityInspector<>(graph);
            String edges;
            boolean isConnected = connectivityInspector.pathExists(s1, s2);
            edges = (s1 + " y " + s2 + " están conectados: " + isConnected + "\n");

            // Obtener todos los componentes conectados
            List<Set<String>> connectedComponents = connectivityInspector.connectedSets();
            edges = edges + "Componentes conectados: " + connectedComponents.toString();
            txtEdges.setText(edges);
        }
        
        
    //
    //DIRECT WEIGHT
        
    //
    public void nextController(ActionEvent event) throws IOException{
        Stage thisStage = (Stage) txtEdges.getScene().getWindow();
        thisStage.close();
        thisStage = null;
    }
}