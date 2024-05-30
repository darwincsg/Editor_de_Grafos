/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.ubi;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author joels
 */
public class GameController {
    /*
    @FXML
    private Button swicth_p1;
    @FXML
    private Label LabelTurn;*/
    @FXML
    private Stage stage;
    
    @FXML
    public GridPane grid;
    
    @FXML
    public Label lbl_movs;
    
    
    @FXML
    public Label lbl_turns;
    
    
    
    private Board P1_Coords;
    private Board P2_Coords;
    
    private boolean turnController;
    
    private int mov_Controller;
    
    private List<Point> Attack_coordsList1 = new ArrayList<>();
    private List<Point> Attack_coordsList2 = new ArrayList<>();
    
    private List<Point> coordsList1 = new ArrayList<>();
    private List<Point> coordsList2 = new ArrayList<>();
    
    public void setBoard1(Board board){
        this.P1_Coords = board;
    }
    
    public void setBoard2(Board board){
        this.P2_Coords = board;
    }
    
    public void setTurn(boolean turn){
        this.turnController = turn;
    }
    
    public void setMovs(int mov_Controller){
        this.mov_Controller = mov_Controller;
    }
    
    public void Attack_setList1(List<Point> Attack_coordsList1){
        this.Attack_coordsList1 = Attack_coordsList1;
    }
    public void Attack_setList2(List<Point> Attack_coordsList2){
        this.Attack_coordsList2 = Attack_coordsList2;
    }
    public void setList1(List<Point> coordsList1){
        this.coordsList1 = coordsList1;
    }
    public void setList2(List<Point> coordsList2){
        this.coordsList2 = coordsList2;
    }
    
    
    public void nextturn_Controller(ActionEvent event) throws Exception{
        if(mov_Controller != 0){
            return;
        }
        
        
        if(Attack_coordsList1.size() == 25){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../resources/game_over.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("WINNER");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            
            
            game_over_controller controller = fxmlLoader.getController();
            controller.change_text("Winner : Player 2");
        }else if(Attack_coordsList2.size() == 25){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../resources/game_over.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("WINNER");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            
            game_over_controller controller = fxmlLoader.getController();
            controller.change_text("Winner: Player 1");
        }else{
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
            controller.setBoard2(P2_Coords);
            controller.setTurn(!turnController);
            controller.setMovs(2);
            controller.Attack_setList1(Attack_coordsList1);
            controller.Attack_setList2(Attack_coordsList2);
            controller.setList1(coordsList1);
            controller.setList2(coordsList2);
            if(turnController){
                controller.initializeData(Attack_coordsList2, coordsList2);
            }else{
                controller.initializeData(Attack_coordsList1, coordsList1);
            }
        }
        
                    
        Stage thisStage = (Stage) grid.getScene().getWindow();
        thisStage.close();
        thisStage = null;
        
    }
    
    public void b_AttackController(ActionEvent event){
    
        Button b = (Button) event.getSource();
        
        Integer row = GridPane.getRowIndex(b);
        Integer col = GridPane.getColumnIndex(b);
        
        if(mov_Controller == 0){
            return;
        }
        
        mov_Controller--;
        
        if(confEvery((int) row, (int) col)){
            
            if(turnController){
                Attack_coordsList1.add(new Point( (int)row, (int)col ));
            }else{
                Attack_coordsList2.add(new Point( (int)row, (int)col ));
            }
            
            b.setStyle("-fx-background-color: red;");
            b.setOnAction(null);
            lbl_movs.setText("Movements: " + mov_Controller);
        }else{
            if(turnController){
                coordsList1.add(new Point( (int)row, (int)col ));
            }else{
                coordsList2.add(new Point( (int)row, (int)col ));
            }
            b.setStyle("-fx-background-color: grey;");
            b.setOnAction(null);
            lbl_movs.setText("Movements: " + mov_Controller);
        }
        
    }
    
    public boolean confirmBattleShips(int x, int y){
        int CoordX, CoordY;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                if(turnController){
                    CoordX = P1_Coords.getBattleShipCoords(i, j).x;
                    CoordY = P1_Coords.getBattleShipCoords(i, j).y;
                }else{
                    CoordX = P2_Coords.getBattleShipCoords(i, j).x;
                    CoordY = P2_Coords.getBattleShipCoords(i, j).y;
                }
                
                if(CoordX == x && CoordY == y){
                    return true;
                }
                
            }
        }
        return false;
    }
    public boolean confirmDestroyerShips(int x, int y){
        int CoordX, CoordY;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 2; j++){
                if(turnController){
                    CoordX = P1_Coords.getDestroyerCoords(i, j).x;
                    CoordY = P1_Coords.getDestroyerCoords(i, j).y;
                }else{
                    CoordX = P2_Coords.getDestroyerCoords(i, j).x;
                    CoordY = P2_Coords.getDestroyerCoords(i, j).y;
                }
                if(CoordX == x && CoordY == y){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean confirmSubmarine(int x, int y){
        int CoordX, CoordY;
        for(int i = 0; i < 3; i++){
            if(turnController){
                CoordX = P1_Coords.getSubmarinesCoords(i).x;
                CoordY = P1_Coords.getSubmarinesCoords(i).y;
            }else{
                CoordX = P2_Coords.getSubmarinesCoords(i).x;
                CoordY = P2_Coords.getSubmarinesCoords(i).y;
            }
            if(CoordX == x && CoordY == y){
                    return true;
            }
        }
        return false;
    }
    public boolean confEvery(int x, int y){
        return confirmBattleShips(x,y) || confirmDestroyerShips(x,y) || confirmSubmarine(x,y);            
               
    }
    
    
    public void initializeData(List<Point> Attack_dataCoords, List<Point> dataCoords ) {
        int index;
        Button b; 
        
        if(turnController){
            lbl_turns.setText("TURN PLAYER 2");
        }else{
            lbl_turns.setText("TURN PLAYER 1");
        }
        
        for(Point point : Attack_dataCoords ){
            index = point.x * grid.getColumnCount() + point.y;
            b = (Button) grid.getChildren().get(index);
            b.setStyle("-fx-background-color: red;");
            b.setOnAction(null);
        }
        for(Point pointC : dataCoords ){
            index = pointC.x * grid.getColumnCount() + pointC.y;
            b = (Button) grid.getChildren().get(index);
            b.setStyle("-fx-background-color: grey;");
            b.setOnAction(null);
        }
    }
    
    
}
