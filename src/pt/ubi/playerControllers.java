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


public class playerControllers {
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
    
    private void invalidadeButtons(){
        
        for(int i = 0; i < 2; i++){
            int index = shipCoords[i].getLocation().x * grid.getColumnCount() + shipCoords[i].getLocation().y ;
            Button b = (Button) grid.getChildren().get(index);
            b.setOnAction(null);
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
        
        System.out.println("Fila " + row + "Columna" + col);
        
        if(BattleShip && countBattleShip>0 ){
            boolean shipB = BattleshipController(row,col);
            if(shipB){
                arrayCount++;
                button.setStyle("-fx-background-color: black;");
                System.out.println("Bool: " + shipB + " Count: " + arrayCount);
                
                if(arrayCount == 3){
                                     
                    arrayCount = 0;
                    countBattleShip--;
                    button.setOnAction(null);
                    invalidadeButtons();
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
                    arrayCount = 0;
                    countDestroyer--;
                    invalidadeButtons();
                    L_Destroyer.setText(String.valueOf(countDestroyer));
                }
            }
        }else if(Submarine && countSubmarine>0 ){
            countSubmarine--;
            button.setOnAction(null);
            button.setStyle("-fx-background-color: red;");
            L_Submarine.setText(String.valueOf(countSubmarine));
        }
        
        
    }    
    
    public void handlerPlayer2(ActionEvent event) throws Exception  {
        if(countSubmarine == 0 && countDestroyer == 0 && countBattleShip == 0){

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../resources/player2_J.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Battleship");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        
    }
    public void PlayController(ActionEvent event) throws Exception  {
        if(countSubmarine == 0 && countDestroyer == 0 && countBattleShip == 0){

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../resources/game_over.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Battleship");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        
    }
    //LISTA CON LOS POINT DE LOS BARCOS HUNDIDOS
}