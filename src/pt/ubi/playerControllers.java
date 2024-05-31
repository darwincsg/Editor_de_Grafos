/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.ubi;

/**
 *
 * @author joels
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import javafx.stage.Modality;
import javafx.stage.StageStyle;


public class playerControllers {
    @FXML
    private Button swicth_p1;
    @FXML
    private Button B_Battle;
    @FXML
    private Button B_Destroyer;
    @FXML
    private Button B_Submarine;
    
    @FXML
    private Label L_Battle;
    @FXML
    private Label L_Destroyer;
    @FXML
    private Label L_Submarine;
    
    @FXML
    private GridPane grid;
    
    @FXML
    private Stage stage;
    
    private Point[] shipCoords = {
            new Point(-1, -1),
            new Point(-1, -1),
            new Point(-1, -1)
        };
    
    
    private boolean BattleShip = true;
    private boolean Destroyer = false;
    private boolean Submarine = false;
    private boolean direcao = false;
    
    private int countBattleShip = 4;
    private int countDestroyer = 5;
    private int countSubmarine = 3;
    private int arrayCount = 0;
    
    //PL1 AND PL2 COORDS
    private Board P1_Coords = new Board();
    private Board P_Coords = new Board();
    
    public void setVisible(boolean v){
        swicth_p1.setVisible(v);
    }
    
    private void invalidadeButtons(int boat){
        
        for(int i = 0; i < 2; i++){
            int index = shipCoords[i].getLocation().x * grid.getColumnCount() + shipCoords[i].getLocation().y ;
            Button b = (Button) grid.getChildren().get(index);
            b.setOnAction(null);
            b.setStyle("");
            switch(boat){
                case 0:
                    P_Coords.setBattleShipCoords(shipCoords[i].getLocation().x, shipCoords[i].getLocation().y, countBattleShip, i);
                    break;
                case 1:
                    P_Coords.setDestroyerCoords(shipCoords[i].getLocation().x, shipCoords[i].getLocation().y, countDestroyer, i);
                    break;
                default:
                    break;
            }
            
        }
    }
    
    private void paintShips(int boat, boolean dir){
        int index1 = shipCoords[0].getLocation().x * grid.getColumnCount() + shipCoords[0].getLocation().y ;
        int index2 = shipCoords[1].getLocation().x * grid.getColumnCount() + shipCoords[1].getLocation().y ;
        
        Button b1 = (Button) grid.getChildren().get(index1);
        Button b2 = (Button) grid.getChildren().get(index2);
        
        switch(boat){
            case 0 -> {
                int index3 = shipCoords[2].getLocation().x * grid.getColumnCount() + shipCoords[2].getLocation().y ;                    
                Button b3 = (Button) grid.getChildren().get(index3);
                b3.setStyle("");
                if(dir){
                    b1.getStyleClass().add("buttonBat1");
                    b2.getStyleClass().add("buttonBat2");
                    b3.getStyleClass().add("buttonBat3");
                    break;
                }
                b1.getStyleClass().add("buttonBatL3");
                b2.getStyleClass().add("buttonBatL2");
                b3.getStyleClass().add("buttonBatL1");
                break;
            }
            case 1 -> {   
                if(dir){
                    b1.getStyleClass().add("buttonDes1");
                    b2.getStyleClass().add("buttonDes2");
                    break;
                }
                b1.getStyleClass().add("buttonDesL2");
                b2.getStyleClass().add("buttonDesL1");
                break;
            }
        
        }
    }
    
    private boolean VerificarColumna_Fila(int x, int y){
        if(shipCoords[0].getLocation().y == y){
            return true; //SE FOR COLUMNA
        }
        return false; //SE FOR FILA
    }
    
    private boolean BattleshipController(int x, int y){
        if(arrayCount == 0){
            
            shipCoords[0].setLocation(x, y);
            
            return true;
        }else if(arrayCount == 1){
            if( shipCoords[0].getLocation().x != x && shipCoords[0].getLocation().y != y){
                return false;
            }
            direcao = VerificarColumna_Fila(x,y);                               //VERIFICAMOS SE E COLUMNA O FILA 
            if(direcao){
                if(x == shipCoords[0].getLocation().x + 1 || x == shipCoords[0].getLocation().x - 1){                    
                    shipCoords[1].setLocation(x, y);
                    
                    return true;
                }
                return false;
            }else{
                if(y == shipCoords[0].getLocation().y + 1 || y == shipCoords[0].getLocation().y - 1){
                    
                    shipCoords[1].setLocation(x, y);
                    
                    return true;
                }
                return false;
            }        
        }else if(arrayCount == 2){
            
            if(direcao){
                if(y == shipCoords[0].getLocation().y){
                    if(shipCoords[0].getLocation().x > shipCoords[1].getLocation().x){
                        return (x == shipCoords[0].getLocation().x + 1 || x == shipCoords[1].getLocation().x - 1);
                    }
                    return (x == shipCoords[0].getLocation().x - 1 || x == shipCoords[1].getLocation().x + 1);
                }
                return false;
            }else{
                if(x == shipCoords[0].getLocation().x){
                    if(shipCoords[0].getLocation().y > shipCoords[1].getLocation().y){
                        return (y == shipCoords[0].getLocation().y + 1 || y == shipCoords[1].getLocation().y - 1);
                    }
                    return (y == shipCoords[0].getLocation().y - 1 || y == shipCoords[1].getLocation().y + 1);
                }
            }
          
        }
        
        return false;
    }
    
    public void handleShip(ActionEvent event){
        Button b = (Button) event.getSource();
        String s_name = b.getText();
        
        if(s_name.equals("Battleship")){
            BattleShip = true;
            Destroyer = false;
            
        }else if (s_name.equals("Destroyer")){
            BattleShip = false;
            Destroyer = true;
        }else{
            BattleShip = false;
            Destroyer = false;
            Submarine = true;
        }
        
    }
    
    public void handleButton(ActionEvent event) {
        Button button = (Button) event.getSource();
        
        Integer row = GridPane.getRowIndex(button);
        Integer col = GridPane.getColumnIndex(button);
        
        //System.out.println("Fila " + row + "Columna" + col);
        
        if(BattleShip && countBattleShip>0 ){
            boolean shipB = BattleshipController(row,col);
            if(shipB){
                arrayCount++;
                button.setStyle("-fx-background-color: black;");
                //System.out.println("Bool: " + shipB + " Count: " + arrayCount);
                
                if(arrayCount == 3){
                    button.setOnAction(null);
                    
                    shipCoords[2].setLocation( (int) row,(int) col);
                    ordenarPuntos(shipCoords);
                    arrayCount = 0;
                    countBattleShip--;
                    invalidadeButtons(0);
                    paintShips(0,direcao);
                    
                    P_Coords.setBattleShipCoords( shipCoords[2].x, shipCoords[2].y,countBattleShip,2);
                            
                    L_Battle.setText(String.valueOf(countBattleShip));
                }
            }
            //button.setStyle("-fx-background-color: black;");
        }else if(Destroyer && countDestroyer>0){
            boolean shipB = BattleshipController(row,col); 
            if(shipB){
                arrayCount++;
                button.setStyle("-fx-background-color: green;");
                if(arrayCount == 2){
                    shipCoords[2].setLocation(11,11);
                    ordenarPuntos(shipCoords);
                    arrayCount = 0;
                    countDestroyer--;
                    invalidadeButtons(1);
                    paintShips(1,direcao);
                    L_Destroyer.setText(String.valueOf(countDestroyer));
                }
            }
        }else if(Submarine && countSubmarine>0 ){
            countSubmarine--;
            button.setOnAction(null);
            button.getStyleClass().add("buttonSub");
            P_Coords.setSubmarinesCoords((int) row, (int) col, countSubmarine);
            L_Submarine.setText(String.valueOf(countSubmarine));
        }
        if(countSubmarine == 0 && countDestroyer == 0 && countBattleShip == 0){
            setVisible(true);
        }
        
    }    
    
    public void handlerPlayer2(ActionEvent event) throws Exception  {
        
        if(countSubmarine == 0 && countDestroyer == 0 && countBattleShip == 0){
            
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../resources/player2_J.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Battleship");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            
            playerControllers controller = fxmlLoader.getController();
            controller.setBoard1(P_Coords);
            controller.setVisible(false);
            
            Stage thisStage = (Stage) L_Battle.getScene().getWindow();
            thisStage.close();
            thisStage = null;
        }
        
    }
    public void PlayController(ActionEvent event) throws Exception  {
        if(countSubmarine == 0 && countDestroyer == 0 && countBattleShip == 0){

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../resources/turns.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Battleship");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            
            GameController controller = fxmlLoader.getController();
            controller.setBoard1(P1_Coords);
            controller.setBoard2(P_Coords);
            controller.setTurn(false);
            controller.setMovs(2);
            controller.setVisible(false);
            
            Stage thisStage = (Stage) L_Battle.getScene().getWindow();
            thisStage.close();
            thisStage = null;
        }
        
    }
    
    public void setBoard1(Board board){
        this.P1_Coords = board;
    }
    
    private static void ordenarPuntos(Point[] puntos) {
        Arrays.sort(puntos, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                int resultado = Integer.compare(p1.x, p2.x);
                if (resultado == 0) {
                    resultado = Integer.compare(p1.y, p2.y);
                }
                return resultado;
            }
        });
    }
    
}
