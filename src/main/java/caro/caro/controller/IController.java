package caro.caro.controller;

import caro.caro.model.BoardState;
import caro.caro.model.Player;
import caro.caro.model.Point;
import caro.caro.view.View;
import javafx.scene.control.Button;

public interface IController {
    // tra ve luot nguoi choi
    int getPlayerFlag();

    // set den luot nguoi choi
    void setPlayerFlag(int playerFlag);

    // toa do cua nguoi choi
    Point AI(int player);

    // danh dau ket thuc
    int checkEnd(int x, int y);

    // Kiem tra chien thang
    BoardState getBoardState();

    // set nguoi choi
    void setPlayer(Player player);

    // bat dau choi
    void play(Button c, Button[][] a);

    // thoat
    boolean isEnd();

    // set ket thuc
    void setEnd(boolean end);

    // set giao dien
    void setView(View view);

    // quay lai nuoc
    void undo(Button[][] a);

    // luu tro choi
    //void save();

    // reset tro choi
    void reset(Button[][] a);

    // mo tro choi da luu
    //void open(Button[][] a);

    // set thoi gian choi cho nguoi choi
    //void setTimePlayer(Labeled timePlayer1, Labeled timePlayer2);

    // chay thoi gian choi
    //void runTimer(int player);

}
