/**
 * Lop computer player
 */
package caro.caro.model;

import java.util.ArrayList;

public class ComputerPlayer implements Player, Setting {
    public boolean cWin = false;
    public Point goPoint;
    EvalBoard eBoard; // diem cua trang thai ban co
    BoardState boardState; // trang thai cua ban co
    int playerFlag = 2; // danh dau la computer player
    int _x, _y; // toa do nuoc di

    public ComputerPlayer(BoardState board) {
        this.boardState = board;
        this.eBoard = new EvalBoard(board.width, board.height);
    }


    //ham heuristic
    public void evaluate(int player) {
        int row, col;
        int maxRow = eBoard.height, maxCol = eBoard.width;
        int ePC, eHuman;
        String key;
        eBoard.resetBoard(); // reset toan bo diem trang thai cua toan bo o co
        // Duyet theo hang
        for (row = 0; row < maxRow; row++)
            for (col = 0; col < maxCol - 4; col++) {
                ePC = 0;
                eHuman = 0;
                key = "";
                for (int i = 0; i < 5; i++) {
                    if (boardState.getPosition(row, col + i) == 1) { // neu quan do la cua human
                        eHuman++;
                        key = key + "1";
                    } else if (boardState.getPosition(row, col + i) == 2) { // neu quan do la cua pc
                        ePC++;
                        key = key + "1";
                    } else {
                        key = key + "0";
                    }
                }
                // trong vong 5 o khong co quan dich
                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (boardState.getPosition(row, col + i) == 0) { // neu o chua danh
                            if (eHuman == 0) // ePC khac 0
                                if (player == 1)
                                    //eBoard.EBoard[row][col + i] += DScore[ePC]; // cho diem phong ngu
                                    eBoard.EBoard[row][col + i] += map.get(key) * defMultiplier;
                                else
                                    eBoard.EBoard[row][col + i] += map.get(key) * atkMultiplier;// cho diem tan cong
                            if (ePC == 0) // eHuman khac 0
                                if (player == 2) {
                                    //System.out.println(key);
                                    eBoard.EBoard[row][col + i] += map.get(key) * defMultiplier;// cho diem phong ngu
                                } else
                                    eBoard.EBoard[row][col + i] += map.get(key) * atkMultiplier;// cho diem tan cong
                            if (eHuman == 4 || ePC == 4)
                                eBoard.EBoard[row][col + i] *= 3;
                        }
                    }
                }
            }

        // Duyet theo cot
        for (row = 0; row < maxRow - 4; row++)
            for (col = 0; col < maxCol; col++) {
                ePC = 0;
                eHuman = 0;
                key = "";
                for (int i = 0; i < 5; i++) {
                    if (boardState.getPosition(row + i, col) == 1) {
                        eHuman++;
                        key += "1";
                    } else if (boardState.getPosition(row + i, col) == 2) {
                        ePC++;
                        key += "1";
                    } else key += "0";
                }
                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (boardState.getPosition(row + i, col) == 0) // Neu o chua duoc danh
                        {
                            if (eHuman == 0)
                                if (player == 1)
                                    eBoard.EBoard[row + i][col] += map.get(key) * defMultiplier;
                                else
                                    eBoard.EBoard[row + i][col] += map.get(key) * atkMultiplier;
                            if (ePC == 0)
                                if (player == 2)
                                    eBoard.EBoard[row + i][col] += map.get(key) * defMultiplier;
                                else
                                    eBoard.EBoard[row + i][col] += map.get(key) * atkMultiplier;
                            if (eHuman == 4 || ePC == 4)
                                eBoard.EBoard[row + i][col] *= 3;
                        }

                    }
                }
            }

        // Duyet theo duong cheo xuong
        for (row = 0; row < maxRow - 4; row++)
            for (col = 0; col < maxCol - 4; col++) {
                ePC = 0;
                eHuman = 0;
                key = "";
                for (int i = 0; i < 5; i++) {
                    if (boardState.getPosition(row + i, col + i) == 1) {
                        eHuman++;
                        key += "1";
                    } else if (boardState.getPosition(row + i, col + i) == 2) {
                        ePC++;
                        key += "1";
                    } else key += "0";
                }
                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (boardState.getPosition(row + i, col + i) == 0) // Neu o chua duoc danh
                        {
                            if (eHuman == 0)
                                if (player == 1)
                                    eBoard.EBoard[row + i][col + i] += map.get(key) * defMultiplier;
                                else
                                    eBoard.EBoard[row + i][col + i] += map.get(key) * atkMultiplier;
                            if (ePC == 0)
                                if (player == 2)
                                    eBoard.EBoard[row + i][col + i] += map.get(key) * defMultiplier;
                                else
                                    eBoard.EBoard[row + i][col + i] += map.get(key) * atkMultiplier;
                            if (eHuman == 4 || ePC == 4)
                                eBoard.EBoard[row + i][col + i] *= 3;
                        }

                    }
                }
            }

        // Duyet theo duong cheo len
        for (row = 4; row < maxRow; row++)
            for (col = 0; col < maxCol - 4; col++) {
                ePC = 0; // so quan PC
                eHuman = 0; // so quan Human
                key = "";
                for (int i = 0; i < 5; i++) {
                    if (boardState.getPosition(row - i, col + i) == 1) {// neu la human
                        eHuman++; // tang so quan human
                        key += "1";
                    } else if (boardState.getPosition(row - i, col + i) == 2) {// neu la PC
                        ePC++; // tang so quan PC
                        key += "1";
                    } else key += "0";
                }
                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (boardState.getPosition(row - i, col + i) == 0) { // neu o chua duoc danh
                            if (eHuman == 0)
                                if (player == 1)
                                    eBoard.EBoard[row - i][col + i] += map.get(key) * defMultiplier;
                                else
                                    eBoard.EBoard[row - i][col + i] += map.get(key) * atkMultiplier;
                            if (ePC == 0)
                                if (player == 2)
                                    eBoard.EBoard[row - i][col + i] += map.get(key) * defMultiplier;
                                else
                                    eBoard.EBoard[row - i][col + i] += map.get(key) * atkMultiplier;
                            if (eHuman == 4 || ePC == 4)
                                eBoard.EBoard[row - i][col + i] *= 3;
                        }

                    }
                }
            }

    }

    // thuat toan alpha-beta
    public void alphaBeta(int alpha, int beta, int depth, int player) {
        //if (player == 2) {
        maxValue(boardState, alpha, beta, depth);

        //} else {
        //    minValue(boardState, alpha, beta, depth);
        //}
    }

    private int maxValue(BoardState state, int alpha, int beta, int depth) {
        //System.out.println(depth);
        eBoard.MaxPos();  // tinh toa do co diem cao nhat
        int value = eBoard.evaluationBoard; // gia tri max hien tai
        if (depth >= maxDepth) {
            return value;
        }
        evaluate(2);
        //evalChessBoard(2, eBoard); // danh gia diem voi nguoi choi hien tai la PC
        ArrayList<Point> list = new ArrayList<>(); // list cac nut con
        int tmp = localBeam;
        if (depth == 0) tmp = localBeam * 2;
        for (int i = 0; i < tmp; i++) {
            Point node = eBoard.MaxPos();
            if (node == null)
                break;
            list.add(node);
            eBoard.EBoard[node.x][node.y] = 0;
        }
        int v = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            Point com = list.get(i);
            state.setPosition(com.x, com.y, 2);
            int mini = minValue(state, alpha, beta, depth + 1);
            if (mini > v) {
                v = mini;
                goPoint = com;
            }
            //v = Math.max(v, minValue(state, alpha, beta, depth + 1));
            state.setPosition(com.x, com.y, 0);
            if ((v >= beta && depth > 0) || state.checkEnd(com.x, com.y) == 2) {
                goPoint = com;
                return v;

            }
            alpha = Math.max(alpha, v);
        }

        return v;
    }

    private int minValue(BoardState state, int alpha, int beta, int depth) {
        eBoard.MaxPos();
        int value = eBoard.evaluationBoard;
        if (depth >= maxDepth) {
            return value;
        }
        evaluate(1);
        ArrayList<Point> list = new ArrayList<>(); // list cac nut con
        for (int i = 0; i < localBeam; i++) {
            Point node = eBoard.MaxPos();
            if (node == null)
                break;
            list.add(node);
            eBoard.EBoard[node.x][node.y] = 0;
        }
        int v = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            Point com = list.get(i);
            state.setPosition(com.x, com.y, 1);
            v = Math.min(v, maxValue(state, alpha, beta, depth + 1));
            state.setPosition(com.x, com.y, 0);
            if ((v <= alpha && depth > 1) || state.checkEnd(com.x, com.y) == 1) {
                return v;
            }
            beta = Math.min(beta, v);
        }
        return v;
    }


    // tinh toan nuoc di
    public Point AI(int player) {
        alphaBeta(0, 1, 0, player);
        Point temp = goPoint;
        if (temp != null) {
            _x = temp.x;
            _y = temp.y;
        }
        return new Point(_x, _y);
    }

    @Override
    public int getPlayerFlag() {
        return playerFlag;
    }

    @Override
    public void setPlayerFlag(int playerFlag) {
        this.playerFlag = playerFlag;
    }

    @Override
    public BoardState getBoardState() {
        return boardState;
    }

    @Override
    public Point movePoint(int player) {
        return AI(player);
    }

}
