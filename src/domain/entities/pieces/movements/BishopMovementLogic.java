package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;

import java.util.ArrayList;
import java.util.List;

public interface BishopMovementLogic {
    default List<Square> getValidMoves(Square[][] board) {
        Piece bishop = (Piece) this;
        List<Square> squareList = new ArrayList<>();
        Square actualSquare = bishop.getSquare();

        int x = actualSquare.getX();
        int y = actualSquare.getY();

        // Check diagonal moves to the top-right
        for (int i = 1; x + i < 8 && y - i >= 0; i++) {
            Square square = board[y - i][x + i];
            if (square.getPiece() == null) {
                squareList.add(square);
            } else if (square.getPiece().getPieceSide() != bishop.getPieceSide()) {
                squareList.add(square);
                break;
            } else {
                break;
            }
        }

        // Check diagonal moves to the top-left
        for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
            Square square = board[y - i][x - i];
            if (square.getPiece() == null) {
                squareList.add(square);
            } else if (square.getPiece().getPieceSide() != bishop.getPieceSide()) {
                squareList.add(square);
                break;
            } else {
                break;
            }
        }

        // Check diagonal moves to the bottom-right
        for (int i = 1; x + i < 8 && y + i < 8; i++) {
            Square square = board[y + i][x + i];
            if (square.getPiece() == null) {
                squareList.add(square);
            } else if (square.getPiece().getPieceSide() != bishop.getPieceSide()) {
                squareList.add(square);
                break;
            } else {
                break;
            }
        }

        // Check diagonal moves to the bottom-left
        for (int i = 1; x - i >= 0 && y + i < 8; i++) {
            Square square = board[y + i][x - i];
            if (square.getPiece() == null) {
                squareList.add(square);
            } else if (square.getPiece().getPieceSide() != bishop.getPieceSide()) {
                squareList.add(square);
                break;
            } else {
                break;
            }
        }

        return squareList;
    }
}
