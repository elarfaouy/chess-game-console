package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public interface KingMovementLogic {
    default List<Square> getValidMoves(Square[][] board) {
        Piece king = (Piece) this;
        List<Square> squareList = new ArrayList<>();
        Square actualSquare = king.getSquare();

        int x = actualSquare.getX();
        int y = actualSquare.getY();

        int[][] possibleMoves = {
                {x + 1, y},
                {x - 1, y},
                {x, y + 1},
                {x, y - 1},
                {x + 1, y + 1},
                {x + 1, y - 1},
                {x - 1, y + 1},
                {x - 1, y - 1}
        };

        for (int[] move : possibleMoves) {
            int newX = move[0];
            int newY = move[1];

            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Square square = board[newY][newX];
                if (square.getPiece() == null || square.getPiece().getPieceSide() != king.getPieceSide()) {
                    squareList.add(square);
                }
            }
        }

        return squareList;
    }

    default boolean canCastle(Square[][] board, Square targetSquare) {
        Piece king = (Piece) this;
        Piece rook = targetSquare.getPiece();

        if (king.isMoved() || rook.isMoved() || king.getPieceSide() != rook.getPieceSide()) {
            return false;
        }

        int startX = Math.min(king.getSquare().getX(), rook.getSquare().getX()) + 1;
        int endX = Math.max(king.getSquare().getX(), rook.getSquare().getX());
        int y = king.getSquare().getY();

        for (int i = startX; i < endX; i++) {
            if (board[y][i].getPiece() != null) {
                return false;
            }
        }

        return true;
    }
}
