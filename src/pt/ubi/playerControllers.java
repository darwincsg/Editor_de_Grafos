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
    
    private int[][] arrayCords = {
        {-1, -1},
        {-1, -1},
        {-1, -1}
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
            int index = arrayCords[i][0] * grid.getColumnCount() + arrayCords[i][1] ;
            Button b = (Button) grid.getChildren().get(index);
            b.setOnAction(null);
        }
    }
    
    private boolean VerificarColumna_Fila(int x, int y){
        if(arrayCords[0][1] == y){
            return true; //SE FOR COLUMNA
        }
        return false; //SE FOR FILA
    }
    
    private boolean BattleshipController(int x, int y){
        if(arrayCount == 0){
            arrayCords[arrayCount][0] = x;
            arrayCords[arrayCount][1] = y;
            return true;
        }else if(arrayCount == 1){
            if(arrayCords[0][0] != x && arrayCords[0][1] != y){
                return false;
            }
            direcao = VerificarColumna_Fila(x,y);
            if(direcao){
                if(x == arrayCords[0][0] + 1 || x == arrayCords[0][0] - 1){
                    arrayCords[1][0] = x;
                    arrayCords[1][1] = y;
                    return true;
                }
                return false;
            }else{
                if(y == arrayCords[0][1] + 1 || y == arrayCords[0][1] - 1){
                    arrayCords[1][0] = x;
                    arrayCords[1][1] = y;
                    return true;
                }
                return false;
            }        
        }else if(arrayCount == 2){
            
            if(direcao){
                if(y == arrayCords[0][1]){
                    if(arrayCords[0][0] > arrayCords[1][0]){
                        return (x == arrayCords[0][0] + 1 || x == arrayCords[1][0] - 1);
                    }
                    return (x == arrayCords[0][0] - 1 || x == arrayCords[1][0] + 1);
                }
                return false;
            }else{
                if(x == arrayCords[0][0]){
                    if(arrayCords[0][1] > arrayCords[1][1]){
                        return (y == arrayCords[0][1] + 1 || y == arrayCords[1][1] - 1);
                    }
                    return (y == arrayCords[0][1] - 1 || y == arrayCords[1][1] + 1);
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
    
}
