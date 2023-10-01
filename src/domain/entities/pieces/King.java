package domain.entities.pieces;

import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.movements.KingMovementLogic;
import domain.enums.PieceSide;

import java.util.List;

public class King extends Piece implements KingMovementLogic {
    public King(PieceSide pieceSide) {
        super(pieceSide);
    }

    @Override
    public String getSymbol() {
        return getPieceSide().equals(PieceSide.WHITE) ? " ♔ " : " ♚ ";
    }

    @Override
    public List<Square> abilityMoves(Square[][] board) {
        return KingMovementLogic.super.getValidMoves(board);
    }
}
