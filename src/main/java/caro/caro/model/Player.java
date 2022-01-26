/**
 * lop dung chung cho computer player va human player
 */
package caro.caro.model;

public interface Player {
    Point movePoint(int player);

    int getPlayerFlag();

    void setPlayerFlag(int playerFlag);

    BoardState getBoardState();
}
