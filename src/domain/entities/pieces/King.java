package domain.entities.pieces;

import domain.entities.Piece;
import domain.entities.Square;
import domain.enums.PieceSide;

import java.util.List;

public class King extends Piece {
    public King(PieceSide pieceSide) {
        super(pieceSide);
    }

    @Override
    public String getSymbol() {
        return getPieceSide().equals(PieceSide.WHITE) ? " ♔ " : " ♚ ";
    }

    @Override
    public List<Square> abilityMoves(Square[][] board) {
        return null;
    }
}
