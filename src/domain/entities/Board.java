package domain.entities;

public class Board {
    private final Square[][] board;

    public Board() {
        this.board = new Square[8][8];
    }

    public Square[][] getBoard() {
        return board;
    }
}
