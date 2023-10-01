package domain.entities.pieces;

import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.movements.KnightMovementLogic;
import domain.enums.PieceSide;

import java.util.List;

public class Knight extends Piece implements KnightMovementLogic {
    public Knight(PieceSide pieceSide) {
        super(pieceSide);
    }

    @Override
    public String getSymbol() {
        return getPieceSide().equals(PieceSide.WHITE) ? " ♘ " : " ♞ ";
    }

    @Override
    public List<Square> abilityMoves(Square[][] board) {
        return KnightMovementLogic.super.getValidMoves(board);
    }
}
