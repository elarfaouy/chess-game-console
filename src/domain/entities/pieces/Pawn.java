package domain.entities.pieces;

import domain.entities.Piece;
import domain.entities.Square;
import domain.enums.PieceSide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(PieceSide pieceSide) {
        super(pieceSide);
    }

    @Override
    public String getSymbol() {
        return getPieceSide().equals(PieceSide.WHITE) ? " ♙ " : " ♟ ";
    }

    @Override
    public List<Square> abilityMoves(Square[][] board) {
        List<Square> squareList = new ArrayList<>();
        Square actualSquare = this.getSquare();

        int operation = this.getPieceSide().equals(PieceSide.WHITE) ? 1 : -1;

        Square up = board[actualSquare.getY() - operation][actualSquare.getX()];
        if (up.getPiece() == null) squareList.add(up);

        if (!this.isMoved()) {
            Square forward = board[actualSquare.getY() - 2 * operation][actualSquare.getX()];
            if (forward.getPiece() == null) squareList.add(forward);
        }

        if (actualSquare.getX() > 0) {
            Square upLeft = board[actualSquare.getY() - operation][actualSquare.getX() - 1];
            if (upLeft.getPiece() != null) squareList.add(upLeft);
        }

        if (actualSquare.getX() < 7) {
            Square upRight = board[actualSquare.getY() - operation][actualSquare.getX() + 1];
            if (upRight.getPiece() != null) squareList.add(upRight);
        }

        return squareList;
    }

    @Override
    public String toString() {
        return "Pawn{" +
                "x=" + this.getSquare().getX() +
                ", y=" + this.getSquare().getY() +
                ", moved=" + this.isMoved() +
                ", side=" + this.getPieceSide() +
                "}";
    }
}
