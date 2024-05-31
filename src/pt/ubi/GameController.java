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
    
    @FXML
    private Button swicth_p1;
    /*@FXML
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
    
    private List<Point> sunkCoords1 = new ArrayList<>();
    private List<Point> sunkCoords2 = new ArrayList<>();
    private int[] indexCountP1 = new int[9];
    private int[] indexCountP2 = new int[9];    
    
    private int confS = -1;
    private int confB = -1;
    
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
    public void setsunkCoords1(List<Point> sunkCoords1){
        this.sunkCoords1 = sunkCoords1;
    }
    public void setsunkCoords2(List<Point> sunkCoords2){
        this.sunkCoords2 = sunkCoords2;
    }
    public void setindexCount1(int[] indexCountP1){
        this.indexCountP1 = indexCountP1;
    }
    public void setindexCount2(int[] indexCountP2){
        this.indexCountP2 = indexCountP2;
    }
    
    public void setVisible(boolean v){
        swicth_p1.setVisible(v);
    }
    
    public void nextturn_Controller(ActionEvent event) throws Exception{
        if(mov_Controller != 0){
            return;
        }
        
        if(sunkCoords2.size() == 12){
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
        }else if(sunkCoords1.size() == 12){
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
            controller.setsunkCoords1(sunkCoords1);
            controller.setsunkCoords2(sunkCoords2);
            controller.setindexCount1(indexCountP1);
            controller.setindexCount2(indexCountP2);
            controller.setVisible(false);
            
            if(turnController){
                controller.initializeData(Attack_coordsList1, coordsList2, sunkCoords1,indexCountP1);
            }else{
                controller.initializeData(Attack_coordsList2, coordsList1, sunkCoords2,indexCountP2);
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
        
        b.setOnAction(null);
        if(mov_Controller == 0){
            return;
        }
        
        int confValue = confEvery((int) row, (int) col);
        mov_Controller--;
        
        switch (confValue) {
            case 0 -> {
                b.getStyleClass().add("buttonSub");
                b.setOnAction(null);
                lbl_movs.setText("Movements: " + mov_Controller);
            }
            case 1 -> {
                if(turnController){
                    confS = confirmSunkDestroyer(indexCountP2);
                    if(confS != -1){
                        boolean dir = VerificarColumna_Fila(P1_Coords.getDestroyerCoords(),confS) ;
                        removerPoints(confS, 2,Attack_coordsList2, P1_Coords.getDestroyerCoords());
                        paintDestroyerShips(confS,dir,P1_Coords.getDestroyerCoords());
                    }else{
                        b.setStyle("-fx-background-color: red;");
                        Attack_coordsList2.add(new Point( (int)row, (int)col ));
                    }
                }else{
                    confS = confirmSunkDestroyer(indexCountP1);
                    if(confS != -1){
                        boolean dir = VerificarColumna_Fila(P2_Coords.getDestroyerCoords(),confS) ;
                        removerPoints(confS, 2,Attack_coordsList1, P2_Coords.getDestroyerCoords());
                        paintDestroyerShips(confS,dir,P2_Coords.getDestroyerCoords());
                    }else{
                        b.setStyle("-fx-background-color: red;");
                        Attack_coordsList1.add(new Point( (int)row, (int)col ));
                    }
                }
                lbl_movs.setText("Movements: " + mov_Controller);
            }
            case 2 ->{
                if(turnController){
                    confB = confirmSunkBattle(indexCountP2);
                    if(confB != -1){
                        boolean dir = VerificarColumna_Fila(P1_Coords.getBattleshipCoords(),confB-5);
                        removerPoints(confB - 5,3,Attack_coordsList2,P1_Coords.getBattleshipCoords());
                        paintBattleShips(confB-5,dir,P1_Coords.getBattleshipCoords());
                    }else{
                        b.setStyle("-fx-background-color: red;");
                        Attack_coordsList2.add(new Point( (int)row, (int)col ));
                    }
                }
                else{
                    confB = confirmSunkBattle(indexCountP1);
                    if(confB != -1){
                        boolean dir = VerificarColumna_Fila(P2_Coords.getBattleshipCoords(),confB-5);
                        removerPoints(confB - 5,3,Attack_coordsList1,P2_Coords.getBattleshipCoords());
                        paintBattleShips(confB-5,dir,P2_Coords.getBattleshipCoords());
                    }else{
                        b.setStyle("-fx-background-color: red;");
                        Attack_coordsList1.add(new Point( (int)row, (int)col ));
                    }
                }
                lbl_movs.setText("Movements: " + mov_Controller);
            }
            default -> {
                if(turnController){
                    coordsList1.add(new Point( (int)row, (int)col ));
                }else{
                    coordsList2.add(new Point( (int)row, (int)col ));
                }   b.setStyle("-fx-background-color: grey;");
                lbl_movs.setText("Movements: " + mov_Controller);
            }
        }
        if(mov_Controller == 0){
            setVisible(true);
        }
    }
    
    public int confirmBattleShips(int x, int y){
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
                System.out.println("X: " + CoordX + "Y:" + CoordY + "///" + x + " " + y);
                System.out.println("X: " + P1_Coords.getBattleShipCoords(3,2) + "Y:" + P1_Coords.getBattleShipCoords(3,2));
                if(CoordX == x && CoordY == y){
                    return i;
                }
            }
        }
        return -1;
    }
    public int confirmDestroyerShips(int x, int y){
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
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int confirmSubmarine(int x, int y){
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
                    return i;
            }
        }
        return -1;
    }
    public int confEvery(int x, int y){
        int b,d,s;
        s = confirmSubmarine(x,y);
        d = confirmDestroyerShips(x,y);
        b = confirmBattleShips(x,y);
        System.out.println("B = " + b);
        if(s != -1){
            if(turnController){
                sunkCoords2.add(new Point(0,s));
            }else{
                sunkCoords1.add(new Point(0,s));
            }
            return 0;
        }else if(d != -1){
            if(turnController){
                if(indexCountP2[d] == 0){
                    sunkCoords2.add(new Point(1,d));
                }
                indexCountP2[d]++;
            }else{
                if(indexCountP1[d] == 0){
                    sunkCoords1.add(new Point(1,d));
                }
                indexCountP1[d]++;
            }
            return 1;
        }else if(b != -1){
            b += 5;
            System.out.println("B = " + b);
            if(turnController){
                if(indexCountP2[b] == 0){
                    sunkCoords2.add(new Point(2,b - 5));
                }
                indexCountP2[b]++;
            }else{
                if(indexCountP1[b] == 0){
                    sunkCoords1.add(new Point(2, b - 5));
                }
                indexCountP1[b]++;
            }
            return 2;
        }
        
        return -1;            
               
    }
    
    public int confirmSunkDestroyer(int[] countP){
        for(int i = 0; i < 5; i++){
            if(countP[i] == 2){
                countP[i] = -1;
                return i;
            } 
        }
        return -1;
    }
    public int confirmSunkBattle(int[] countP){
        for(int i = 5; i < 9; i++){
            if(countP[i] == 3){
                countP[i] = -1;
                return i;
            } 
        }
        return -1;
    }
    
    public void paintDestroyerShips(int index, boolean dir,Point[][] shipCoords){
        
            int b_index;
            Button b; 
            for(int i = 0, j = 2; i < 2; i++, j--){
                b_index= shipCoords[index][i].x * grid.getColumnCount() + shipCoords[index][i].y;
                b = (Button) grid.getChildren().get(b_index);
                b.setStyle("");
                b.setOnAction(null);
                if(dir){
                    String s = "buttonDes" + (i+1);
                    b.getStyleClass().add(s);
                }else{
                    String s = "buttonDesL" + (j);
                    b.getStyleClass().add(s);
                }
            }
    }
    public void paintBattleShips(int index, boolean dir,Point[][] shipCoords){
        
            int b_index;
            Button b; 
            for(int i = 0, j = 3; i < 3; i++, j--){
                b_index= shipCoords[index][i].x * grid.getColumnCount() + shipCoords[index][i].y;
                b = (Button) grid.getChildren().get(b_index);
                b.setStyle("");
                b.setOnAction(null);
                if(dir){
                    String s = "buttonBat" + (i+1);
                    b.getStyleClass().add(s);
                }else{
                    String s = "buttonBatL" + (j);
                    b.getStyleClass().add(s);
                }
            }
    }
    
    private boolean VerificarColumna_Fila(Point[][] board,int index){
        if(board[index][1].getLocation().y == board[index][0].getLocation().y){
            return true; //SE FOR COLUMNA
        }
        return false; //SE FOR FILA
    }
    
    public void removerPoints(int index, int length, List<Point> list,Point[][] shipCoords){
        for(int i = 0; i < length; i++){
            list.remove(shipCoords[index][i]);
        }
    }
    
    public void initializeData(List<Point> Attack_dataCoords, List<Point> dataCoords, List<Point> sunkCoords, int[] sunkShips) {
        int index, index2, index3;
        Button b; 
                
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
        
        if(turnController){
            lbl_turns.setText("TURN PLAYER 2");
            
            for(Point pointSunk : sunkCoords){
                if(pointSunk.x == 0){
                    Point aux = P1_Coords.getSubmarinesCoords(pointSunk.y);
                    index = aux.x * grid.getColumnCount() + aux.y;
                    b = (Button) grid.getChildren().get(index);
                    b.getStyleClass().add("buttonSub");
                    b.setOnAction(null);
                }
                if(pointSunk.x == 1){
                    for(int i = 0; i < 5; i++){
                        if(sunkShips[i] == -1){
                            boolean dir = VerificarColumna_Fila(P1_Coords.getDestroyerCoords(),i);
                            paintDestroyerShips(i, dir,P1_Coords.getDestroyerCoords());
                        }
                    }
                }
                if(pointSunk.x == 2){
                    for(int i = 5; i < 9; i++){
                        if(sunkShips[i] == -1){
                           boolean dir = VerificarColumna_Fila(P1_Coords.getBattleshipCoords(),i-5); 
                            paintBattleShips(i-5, dir,P1_Coords.getBattleshipCoords());
                        }
                    }
                }
            }
            
        }else{
            lbl_turns.setText("TURN PLAYER 1");
            
            for(Point pointSunk : sunkCoords){
                if(pointSunk.x == 0){
                    Point aux = P2_Coords.getSubmarinesCoords(pointSunk.y);
                    index = aux.x * grid.getColumnCount() + aux.y;
                    b = (Button) grid.getChildren().get(index);
                    b.getStyleClass().add("buttonSub");
                    b.setOnAction(null);
                }
                if(pointSunk.x == 1){
                    for(int i = 0; i < 5; i++){
                        if(sunkShips[i] == -1){
                            boolean dir = VerificarColumna_Fila(P2_Coords.getDestroyerCoords(),i);
                            paintDestroyerShips(i, dir,P2_Coords.getDestroyerCoords());
                        } 
                    }
                }
                if(pointSunk.x == 2){
                    for(int i = 5; i < 9; i++){
                        if(sunkShips[i] == -1){
                           boolean dir = VerificarColumna_Fila(P2_Coords.getBattleshipCoords(),i-5); 
                            paintBattleShips(i-5, dir,P2_Coords.getBattleshipCoords());
                        }
                    }
                }
            }
            
        }
        
    }
    
    
}
