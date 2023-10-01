package domain.entities.pieces;

import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.movements.BishopMovementLogic;
import domain.entities.pieces.movements.RookMovementLogic;
import domain.enums.PieceSide;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece implements RookMovementLogic, BishopMovementLogic {
    public Queen(PieceSide pieceSide) {
        super(pieceSide);
    }

    @Override
    public String getSymbol() {
        return getPieceSide().equals(PieceSide.WHITE) ? " ♕ " : " ♛ ";
    }

    @Override
    public List<Square> abilityMoves(Square[][] board) {
        return getValidMoves(board);
    }

    @Override
    public List<Square> getValidMoves(Square[][] board) {
        List<Square> list = new ArrayList<>();
        list.addAll(RookMovementLogic.super.getValidMoves(board));
        list.addAll(BishopMovementLogic.super.getValidMoves(board));
        return list;
    }
}
