/**
 * Lop dieu khien chinh
 */
package caro.caro.controller;

import caro.caro.model.BoardState;
import caro.caro.model.HumanPlayer;
import caro.caro.model.Player;
import caro.caro.model.Point;
import caro.caro.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Controller implements IController {
    public View view;
    Queue<Point> queue;
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {

        }
    };
    private Player player;
    private Stack<Point> stack; // ngan xep luu cac nuoc da di
    private Class<?> classImg; //  lay anh quan co
    private URL o;
    private URL x;
    private URL oFocus;
    private Image imageO;
    private Image imageOfocus;
    public Image imageX;
    private boolean end;
    private int tongNuocDi;
    private String playerWin;

    public Controller() {
        getComponents();
    }

    private void getComponents() {
        end = false;
        tongNuocDi = 0;
        playerWin = "";
        stack = new Stack<>();
        classImg = this.getClass();
        o = classImg.getResource("/o.png");
        x = classImg.getResource("/x.png");
        oFocus = classImg.getResource("/focus.png");
        imageO = new Image(String.valueOf(o));
        imageX = new Image(String.valueOf(x));
        imageOfocus = new Image(String.valueOf(oFocus));
    }

    @Override
    public Point AI(int player) {
        return this.player.movePoint(player);
    }

    @Override
    public int getPlayerFlag() {
        return player.getPlayerFlag();
    }

    @Override
    public void setPlayerFlag(int playerFlag) {
        player.setPlayerFlag(playerFlag);
    }

    @Override
    public BoardState getBoardState() {
        return player.getBoardState();
    }

    @Override
    public int checkEnd(int x, int y) {
        return player.getBoardState().checkEnd(x, y);
    }

    @Override
    public boolean isEnd() {
        return end;
    }

    @Override
    public void setEnd(boolean end) {
        this.end = end;
    }

    @Override
    public void play(Button c, Button[][] a) {
        StringTokenizer tokenizer = new StringTokenizer(c.getAccessibleText(), ";");
        int x = Integer.parseInt(tokenizer.nextToken());
        int y = Integer.parseInt(tokenizer.nextToken());
        //
        if (getBoardState().getPosition(x, y) == 0) {
            danhCo(x, y, 1, a);
            soundClick();
            setPlayerFlag(2);
        }
        if (getPlayerFlag() == 2 && !end) {
            Point p = new Point(0,0);
            if(tongNuocDi == 1 ){
                Point temp = stack.peek();
                p.x = temp.x;
                if(temp.y > 1){
                    p.y = temp.y - 1;
                }
                else{
                    p.y = temp.y + 1;
                }
            }
            else p = AI(2);
            danhCo(p.x, p.y, 2, a);
            setPlayerFlag(1);
        }
        if (end) {
            System.out.println("tong nuoc di la: " + tongNuocDi);
            if (playerWin.equals("3")) {
                dialog("Hòa rồi!!");
            }
            if (playerWin.equals("2")) {
                dialog("Bạn đã thua rồi!!");
            } else {
                setPlayerFlag(1);
                dialog("Bạn đã chiến thắng!");
            }
            return;
        }
    }

    public void danhCo(int x, int y, int player, Button[][] arrayButtonChess) {
        getBoardState().setPosition(x, y, player);
        if (player == 1) {
            arrayButtonChess[x][y].setGraphic(new ImageView(imageX));
            Point point = new Point(x, y);
            point.setPlayer(1);
            if (!stack.empty()) {
                Point prev = stack.peek();
                arrayButtonChess[prev.x][prev.y].setStyle("-fx-background-color: none; ");
            }
            stack.push(point);
            tongNuocDi++;
        } else {
            //arrayButtonChess[x][y].setGraphic(new ImageView(imageOfocus));
            arrayButtonChess[x][y].setGraphic(new ImageView(imageO));
            arrayButtonChess[x][y].setStyle("-fx-background-color: orange; ");
            Point point = new Point(x, y);
            point.setPlayer(2);
            stack.push(point);
            tongNuocDi++;
        }
        if (getBoardState().checkEnd(x, y) == player) {
            playerWin = player + "";
            //System.out.println(player);
            end = true;
        }
        if (tongNuocDi == (getBoardState().height * getBoardState().width)) {
            playerWin = 3 + "";
            end = true;
        }

    }

    private void soundClick() {
        try {
            Clip clip = AudioSystem.getClip();
            URL click = this.getClass().getResource("/Mouse Click Fast.wav");
            //System.out.println(click);
            //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("/Mouse Click Fast.wav"));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(click);
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void print(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j]);
            }
            System.out.println();
        }
    }


    // quay lại 1 nuoc co
    @Override
    public void undo(Button[][] arrayButtonChess) {
        if (!stack.isEmpty()) {
            tongNuocDi--;
            Point point = stack.pop();
            getBoardState();
            BoardState.boardArr[point.x][point.y] = 0;
            arrayButtonChess[point.x][point.y].setGraphic(null);
            arrayButtonChess[point.x][point.y].setStyle("-fx-background-color: white");
        }
    }

    public EventHandler<ActionEvent> action(String action) {
        return null;
    }

    public void dialog(String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Trò chơi kết thúc");
        alert.setHeaderText(title);
        alert.setContentText("Bạn có muốn chơi lại");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //if (getPlayer() instanceof HumanPlayer) {
            //  view.replayHuman();
            //} else {
            view.replayComputer();
            //}
        } else {
            // su dung khi chon khong hoac dong hoi thoai
            reset(view.arrayButtonChess);
            view.getBtnComputer().setDisable(false);
        }
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void reset(Button[][] arrayButtonChess) {
        tongNuocDi = 0;
        getBoardState().resetBoard();
        for (int i = 0; i < arrayButtonChess.length; i++) {
            for (int j = 0; j < arrayButtonChess[i].length; j++) {
                arrayButtonChess[i][j].setGraphic(null);
                arrayButtonChess[i][j].setStyle("-fx-background-color: white");
            }
        }
    }
}
