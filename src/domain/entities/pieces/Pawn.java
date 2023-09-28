package domain.entities.pieces;

import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.movements.PawnMovementLogic;
import domain.enums.PieceSide;

import java.util.List;

public class Pawn extends Piece implements PawnMovementLogic {
    public Pawn(PieceSide pieceSide) {
        super(pieceSide);
    }

    @Override
    public String getSymbol() {
        return getPieceSide().equals(PieceSide.WHITE) ? " ♙ " : " ♟ ";
    }

    @Override
    public List<Square> abilityMoves(Square[][] board) {
        return PawnMovementLogic.super.getValidMoves(board);
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
