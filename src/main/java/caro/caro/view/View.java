/**
 * Giao dien chinh cua tro choi
 */
package caro.caro.view;

import caro.caro.controller.Controller;
import caro.caro.model.BoardState;
import caro.caro.model.ComputerPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class View implements EventHandler<ActionEvent> {
    public static final int WIDTH_BOARD = 24;
    public static final int HEIGHT_BOARD = 24;
    public static final int WIDTH_PANE = 840;
    public static final int HEIGHT_PANE = 800;
    // khung view
    public static Stage primaryStage;
    // mang quan co khi danh
    public Button[][] arrayButtonChess;
    // lop dieu khien
    Controller controller;
    private Button btnComputer;
    private Button btnExit;
    private Button btnUndo;
    private Labeled timePlayer1, timePlayer2;
    private BoardState boardState;
    private ComputerPlayer computer;

    public View() {
    }

    public void start(Stage primaryStage) {
        try {
            View.primaryStage = primaryStage;
            arrayButtonChess = new Button[WIDTH_BOARD][HEIGHT_BOARD];
            boardState = new BoardState(WIDTH_BOARD, HEIGHT_BOARD);
            computer = new ComputerPlayer(boardState);
            controller = new Controller();
            controller.setView(this);
            controller.setPlayer(computer);

            BorderPane borderPane = new BorderPane();
            BorderPane borderPaneBottom = new BorderPane();
            menu(borderPaneBottom);

            GridPane root = new GridPane();
            Scene scene = new Scene(borderPane, WIDTH_PANE, HEIGHT_PANE);
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            borderPane.setPadding(new Insets(20));
            borderPane.setCenter(root);
            borderPane.setBottom(borderPaneBottom);
            // mac dinh player 1 di truoc
            controller.setPlayerFlag(1);
            //controller.setTimePlayer(timePlayer1, timePlayer2);
            for (int i = 0; i < WIDTH_BOARD; i++) {
                for (int j = 0; j < HEIGHT_BOARD; j++) {
                    Button button = new Button();
                    button.setPrefSize(40, 40);
                    button.setMinSize(10, 10);
                    button.setAccessibleText(i + ";" + j);
                    arrayButtonChess[i][j] = button;
                    root.add(button, j, i);
                    button.setOnAction(event -> {
                        if (!controller.isEnd()) {
                            controller.play(button, arrayButtonChess);
                        }
                    });
                }
            }
            primaryStage.setScene(scene);
            primaryStage.setTitle("Game caro");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void menu(BorderPane pane) {
        VBox box = new VBox();
        box.setSpacing(10);
        Class<?> clazz = this.getClass();
        AnchorPane anchorPaneMenu = new AnchorPane();

        // Computer
        btnComputer = new Button("Bắt đầu");
        btnComputer.setId("btnMenu");
        btnComputer.setOnAction(this);
        AnchorPane.setTopAnchor(btnComputer, 10.0);
        AnchorPane.setLeftAnchor(btnComputer, 30.0);
        AnchorPane.setRightAnchor(btnComputer, 30.0);
        anchorPaneMenu.getChildren().add(btnComputer);

        // Undo
        btnUndo = new Button("Undo");
        btnUndo.setId("btnMenu");
        btnUndo.setOnAction(this);
        AnchorPane.setTopAnchor(btnUndo, 50.0);
        AnchorPane.setLeftAnchor(btnUndo, 30.0);
        AnchorPane.setRightAnchor(btnUndo, 30.0);
        anchorPaneMenu.getChildren().add(btnUndo);

        // exit
        btnExit = new Button("Thoát");
        btnExit.setId("btnMenu");
        btnExit.setOnAction(this);
        AnchorPane.setTopAnchor(btnExit, 90.0);
        AnchorPane.setLeftAnchor(btnExit, 30.0);
        AnchorPane.setRightAnchor(btnExit, 30.0);
        anchorPaneMenu.getChildren().add(btnExit);
        //
        box.getChildren().add(anchorPaneMenu);
        pane.setCenter(box);

    }


    @Override
    public void handle(ActionEvent e) {
        if (e.getSource() == btnExit) {
            primaryStage.close();
            System.exit(0);
        }
        if (e.getSource() == btnComputer) {
            btnComputer.setDisable(true);
            replayComputer();
        }
        if (e.getSource() == btnUndo) {
            controller.undo(arrayButtonChess);
            controller.undo(arrayButtonChess);
        }
    }

    // che do dau voi may
    public void replayComputer() {

        controller.setEnd(false);
        //controller.setTimePlayer(timePlayer1, timePlayer2);
        controller.setPlayer(new ComputerPlayer(new BoardState(WIDTH_BOARD, HEIGHT_BOARD)));
        controller.reset(arrayButtonChess);
        gameMode();

    }

    // xet xem ai di truoc
    public void gameMode() {
        Alert gameMode = new Alert(AlertType.CONFIRMATION);
        gameMode.setTitle("Chọn người chơi trước");
        gameMode.setHeaderText("Bạn có muốn chơi trước không ?");
        Optional<ButtonType> result = gameMode.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            controller.danhCo(WIDTH_BOARD / 2 - 1, HEIGHT_BOARD / 2, 2, arrayButtonChess);
            controller.setPlayerFlag(1);
        } else {
            controller.setPlayerFlag(1);
        }
    }

    public Button getBtnComputer() {
        return btnComputer;
    }
}
