package pt.ubi;

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

import java.io.IOException;

public class HelloController {

    // Labels to provide instructions and information for player 1 and 2
    @FXML
    public Label instruct_p1;
    @FXML
    public Label instruct_p2;

    // Labels for counting the remaining battleships, destroyers, and submarines for player 1 and 2
    @FXML
    public Label b_cnt_p1;
    @FXML
    public Label b_cnt_p2;
    @FXML
    public Label d_cnt_p1;
    @FXML
    public Label d_cnt_p2;
    @FXML
    public Label s_cnt_p1;
    @FXML
    public Label s_cnt_p2;

    // Stage and layout elements for game
    @FXML
    private Stage stage;
    @FXML
    public AnchorPane anchor;
    @FXML
    public GridPane grid;
    @FXML
    public GridPane grid_p2;
    @FXML
    public GridPane grid_t1;
    @FXML
    public GridPane grid_t2;

    // Arrays to track the state of buttons and game logic
    public boolean[][] btns = new boolean[9][9];
    static public boolean[][] player1 = new boolean[9][9];
    static public boolean[][] player2 = new boolean[9][9];
    static public int[][] play1 = new int[9][9];
    static public int[][] play2 = new int[9][9];

    // Flags to manage game turns and hits
    static public boolean t1;
    static public boolean t2;
    static public int move1 = 0;
    static public int hit1 = 0;
    static public int move2 = 0;
    static public int hit2 = 0;
    
    // Labels to display moves and remaining buttons
    @FXML
    public Label m1;
    @FXML
    public Label m2;
    @FXML
    public Label b1;
    @FXML
    public Label b2;
    
    // Buttons to manage turns and switching players
    @FXML
    public Button turn1;
    @FXML
    public Button swicth_p1;
    @FXML
    public Label turn_p1;
    @FXML
    public Label turn_p2;
    
    // Default number of buttons to manage hits
    public int button_no = 2;

    // Variables to track current game state
    public boolean battle = true;
    public int battle_btn = 0;
    public int destroy_btn = 0;
    public int d_num = 0;
    public int submarine_btn = 0;
    public boolean destroy = false;
    public boolean submarine = false;
    public boolean flag = true;

    // Method to initialize player 1 board
    public void p1_board(ActionEvent e) throws IOException {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button = new Button();
                button.setPrefSize(70, 70);

                // Add event handler to change color on click
                button.setOnAction(event
                        -> handleButtonp1(button)
                );

                // Add button to the GridPane
                grid.add(button, i, j);
                player1[i][j] = false;
            }
        }
    }

    // Method to initialize player 2 board
    public void p2_board(ActionEvent e) throws IOException {
        bool_reset();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button = new Button();
                button.setPrefSize(70, 70);

                // Add event handler to change color on click
                button.setOnAction(event
                        -> handleButtonp2(button)
                );

                // Add button to the GridPane
                grid_p2.add(button, i, j);
                player2[i][j] = false;
            }
        }
    }

    // Method to display player 1 turn board
    public void turn1_board() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button = new Button();
                button.setPrefSize(70, 70);

                // Add event handler to change color on click
                button.setOnAction(event
                        -> handleButtont1(button)
                );
                if (play1[i][j] == 1) {
                    button.setStyle("-fx-background-color: gray;");
                } else if (play1[i][j] == 2) {
                    button.setStyle("-fx-background-color: red;");
                }
                // Add button to the GridPane
                grid_t1.add(button, j, i);
            }
        }
        t1 = true;
        t2 = false;
        m1.setText("Move count: " + move1);
        b1.setText("Buttons left: " + (button_no - hit1));
    }

    // Method to display player 2 turn board
    public void turn2_board(ActionEvent e) throws IOException {
        bool_reset();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button = new Button();
                button.setPrefSize(70, 70);

                // Add event handler to change color on click
                button.setOnAction(event
                        -> handleButtont2(button)
                );
                if (play2[i][j] == 1) {
                    button.setStyle("-fx-background-color: gray;");
                } else if (play2[i][j] == 2) {
                    button.setStyle("-fx-background-color: red;");
                }
                // Add button to the GridPane
                grid_t2.add(button, j, i);
            }
        }
        t1 = false;
        t2 = true;
        m2.setText("Move count: " + move2);
        b2.setText("Buttons left: " + (button_no - hit2));
    }

    // Reset boolean values for game state
    public void bool_reset() {
        battle = true;
        battle_btn = 0;
        destroy_btn = 0;
        d_num = 0;
        submarine_btn = 0;
        destroy = false;
        submarine = false;
    }

    // Reset button states
    public void btns_reset() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                btns[i][j] = false;
            }
        }
    }

    // Store state for player 1
    public void store_p1() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                player1[i][j] = false;
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                player1[i][j] = btns[i][j];
                if (player1[i][j]) {
                    System.out.print("T1 ");
                } else {
                    System.out.print("F1 ");
                }
            }
            System.out.print("\n");
        }
    }

    // Store state for player 2
    public void store_p2() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                player2[i][j] = false;
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                player2[i][j] = btns[i][j];
                if (player2[i][j]) {
                    System.out.print("T2 ");
                } else {
                    System.out.print("F2 ");
                }
            }
            System.out.print("\n");
        }
    }

    // Handle actions on player 1's buttons
    public void handleButtonp1(Button b) {
        int row = grid.getRowIndex(b);
        int col = grid.getColumnIndex(b);
        if (battle) {
            if (battle_btn == 0) {
                b.setStyle("-fx-background-color: black;");
                System.out.println("row: " + row + ", col: " + col);
                System.out.println("b-0");
                player1[row][col] = true;
                battle_btn++;
            } else if (battle_btn == 1) {
                if ((row > 0 && player1[row - 1][col]) || (row < 8 && player1[row + 1][col]) || (col > 0 && player1[row][col - 1]) || (col < 8 && player1[row][col + 1])) {
                    b.setStyle("-fx-background-color: black;");
                    System.out.println("row: " + row + ", col: " + col);
                    System.out.println("b-1");
                    player1[row][col] = true;
                    battle_btn++;
                }
            } else if (battle_btn == 2) {
                int r1 = row - 2, r2 = row + 2, c3 = col - 2, c4 = col + 2;
                if (row > 1 && player1[row - 1][col] && player1[r1][col] || (row < 7 && player1[row + 1][col] && player1[r2][col]) || (col > 1 && player1[row][col - 1] && player1[row][c3]) || (col < 7 && player1[row][col + 1] && player1[row][c4])) {
                    b.setStyle("-fx-background-color: black;");
                    System.out.println("row: " + row + ", col: " + col);
                    System.out.println("b-2");
                    player1[row][col] = true;
                    battle_btn++;
                    b_cnt_p1.setText("Battleship: 0");
                    battle = false;
                    destroy = true;
                }
            } else {
                return;
            }
        } else if (destroy) {
            if (destroy_btn == 0) {
                b.setStyle("-fx-background-color: blue;");
                player1[row][col] = true;
                destroy_btn++;
            } else if (destroy_btn == 1) {
                if (player1[row - 1][col] || player1[row + 1][col] || player1[row][col - 1] || player1[row][col + 1]) {
                    b.setStyle("-fx-background-color: blue;");
                    player1[row][col] = true;
                    destroy_btn++;
                    d_num++;
                    d_cnt_p1.setText("Destroyer: " + (2 - d_num));
                    if (d_num == 2) {
                        destroy = false;
                        submarine = true;
                    }
                }
            }
            if (destroy_btn == 2) {
                destroy_btn = 0;
            }
        } else if (submarine) {
            if (submarine_btn < 3) {
                b.setStyle("-fx-background-color: red;");
                player1[row][col] = true;
                submarine_btn++;
                s_cnt_p1.setText("Submarine: " + (3 - submarine_btn));
            } else {
                submarine = false;
            }
        }
    }

    // Handle actions on player 2's buttons
    public void handleButtonp2(Button b) {
        int row = grid_p2.getRowIndex(b);
        int col = grid_p2.getColumnIndex(b);
        if (battle) {
            if (battle_btn == 0) {
                b.setStyle("-fx-background-color: black;");
                System.out.println("row: " + row + ", col: " + col);
                player2[row][col] = true;
                System.out.println("b-0");
                battle_btn++;
            } else if (battle_btn == 1) {
                if ((row > 0 && player2[row - 1][col]) || (row < 8 && player2[row + 1][col]) || (col > 0 && player2[row][col - 1]) || (col < 8 && player2[row][col + 1])) {
                    b.setStyle("-fx-background-color: black;");
                    player2[row][col] = true;
                    System.out.println("row: " + row + ", col: " + col);
                    System.out.println("b-1");
                    battle_btn++;
                }
            } else if (battle_btn == 2) {
                int r1 = row - 2, r2 = row + 2, c3 = col - 2, c4 = col + 2;
                if (row > 1 && player2[row - 1][col] && player2[r1][col] || (row < 7 && player2[row + 1][col] && player2[r2][col]) || (col > 1 && player2[row][col - 1] && player2[row][c3]) || (col < 7 && player2[row][col + 1] && player2[row][c4])) {
                    b.setStyle("-fx-background-color: black;");
                    player2[row][col] = true;
                    System.out.println("row: " + row + ", col: " + col);
                    System.out.println("b-2");
                    battle_btn++;
                    b_cnt_p2.setText("Battleship: 0");
                    battle = false;
                    destroy = true;
                }
            } else {
                return;
            }
        } else if (destroy) {
            if (destroy_btn == 0) {
                b.setStyle("-fx-background-color: blue;");
                player2[row][col] = true;
                destroy_btn++;
            } else if (destroy_btn == 1) {
                if (player2[row - 1][col] || player2[row + 1][col] || player2[row][col - 1] || player2[row][col + 1]) {
                    b.setStyle("-fx-background-color: blue;");
                    player2[row][col] = true;
                    destroy_btn++;
                    d_num++;
                    d_cnt_p2.setText("Destroyer: " + (2 - d_num));
                    if (d_num == 2) {
                        destroy = false;
                        submarine = true;
                    }
                }
            }
            if (destroy_btn == 2) {
                destroy_btn = 0;
            }
        } else if (submarine) {
            if (submarine_btn < 3) {
                b.setStyle("-fx-background-color: red;");
                player2[row][col] = true;
                submarine_btn++;
                s_cnt_p2.setText("Submarine: " + (3 - submarine_btn));
            } else {
                submarine = false;
            }
        }
    }

    // Handle actions for player 1's turn
    public void handleButtont1(Button b) {
        if (t2) {
            return;
        }
        int row = GridPane.getRowIndex(b);
        int col = GridPane.getColumnIndex(b);
        move1++;
        m1.setText("Move count: " + move1);
        if (player2[row][col]) {
            System.out.print("T ");
        } else {
            System.out.print("F ");
        }
        if (!player2[row][col]) {
            b.setStyle("-fx-background-color: gray;");
            play1[row][col] = 1;
        } else if (player2[row][col]) {
            b.setStyle("-fx-background-color: red;");
            play1[row][col] = 2;
            hit1++;
            b1.setText("Buttons left: " + (button_no - hit1));
            if (hit1 == button_no) {
                swicth_p1.setText("END");
                swicth_p1.setOnAction(event -> {
                    try {
                        game_over(event, "../resources/game_over.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        t1 = false;
        t2 = true;
    }

    // Handle actions for player 2's turn
    public void handleButtont2(Button b) {
        if (t1) {
            return;
        }
        int row = grid_t2.getRowIndex(b);
        int col = grid_t2.getColumnIndex(b);
        move2++;
        m2.setText("Move count: " + move2);
        if (!player1[row][col]) {
            b.setStyle("-fx-background-color: gray;");
            play2[row][col] = 1;
        } else if (player1[row][col]) {
            b.setStyle("-fx-background-color: red;");
            play2[row][col] = 2;
            hit2++;
            b2.setText("Buttons left: " + (button_no - hit2));
            if (hit2 == button_no) {
                turn1.setText("END");
                turn1.setOnAction(event -> {
                    try {
                        game_over(event, "../resources/game_over2.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        t1 = true;
        t2 = false;
    }

    // Method to handle game over scenario and switch to game over screen
    public void game_over(ActionEvent e, String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(file));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Battleship");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    // Method to switch to player 1 screen
    public void switch_to_p1(ActionEvent e) throws IOException {
        store_p2();
        btns_reset();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("../resources/player1.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Battleship p1");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Method to start the game for player 1
    public void begin_(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("../resources/player1_J.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Battleship p1");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Method to save the state of player 1
    @FXML
    public void savep1() {
        store_p1();
        btns_reset();
    }

    // Method to save the state of player 2
    public void savep2() {
        store_p2();
        btns_reset();
    }

    // Method to switch to player 2 screen
    public void switch_to_p2(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("../resources/player2.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Battleship p2");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Method to switch to turn 1 screen
    public void turn1(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("../resources/turn1.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Battleship");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Method to switch to turn 2 screen
    public void turn2(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("../resources/turn2.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Battleship");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
