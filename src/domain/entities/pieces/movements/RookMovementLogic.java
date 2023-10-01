package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;

import java.util.ArrayList;
import java.util.List;

public interface RookMovementLogic {
    default List<Square> getValidMoves(Square[][] board) {
        Piece rook = (Piece) this;
        List<Square> squareList = new ArrayList<>();
        Square actualSquare = rook.getSquare();

        int x = actualSquare.getX();
        int y = actualSquare.getY();

        // Check horizontal moves to the right
        for (int i = x + 1; i < 8; i++) {
            Square square = board[y][i];
            if (square.getPiece() == null) {
                squareList.add(square);
            } else if (square.getPiece().getPieceSide() != rook.getPieceSide()) {
                squareList.add(square);
                break;
            } else {
                break;
            }
        }

        // Check horizontal moves to the left
        for (int i = x - 1; i >= 0; i--) {
            Square square = board[y][i];
            if (square.getPiece() == null) {
                squareList.add(square);
            } else if (square.getPiece().getPieceSide() != rook.getPieceSide()) {
                squareList.add(square);
                break;
            } else {
                break;
            }
        }

        // Check vertical moves upwards
        for (int i = y - 1; i >= 0; i--) {
            Square square = board[i][x];
            if (square.getPiece() == null) {
                squareList.add(square);
            } else if (square.getPiece().getPieceSide() != rook.getPieceSide()) {
                squareList.add(square);
                break;
            } else {
                break;
            }
        }

        // Check vertical moves downwards
        for (int i = y + 1; i < 8; i++) {
            Square square = board[i][x];
            if (square.getPiece() == null) {
                squareList.add(square);
            } else if (square.getPiece().getPieceSide() != rook.getPieceSide()) {
                squareList.add(square);
                break;
            } else {
                break;
            }
        }

        return squareList;
    }
}
