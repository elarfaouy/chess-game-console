package domain.entities;

import domain.entities.pieces.King;
import domain.enums.PieceSide;

import java.util.Arrays;

public class Board {
    private final Square[][] board;

    public Board() {
        this.board = new Square[8][8];
    }

    public Square[][] getBoard() {
        return board;
    }

    public Square getSquare(int row, int col) {
        return board[row][col];
    }

    public Square findKing(PieceSide side) {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(x -> {
                    Piece piece = x.getPiece();
                    return piece != null && piece.getPieceSide().equals(side) && piece instanceof King;
                })
                .findFirst()
                .orElse(null); // this in chess game doesn't happen
    }
}
