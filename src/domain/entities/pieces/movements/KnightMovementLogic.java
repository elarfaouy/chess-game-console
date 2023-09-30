package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;

import java.util.ArrayList;
import java.util.List;

public interface KnightMovementLogic {
    default List<Square> getValidMoves(Square[][] board) {
        Piece knight = (Piece) this;
        List<Square> squareList = new ArrayList<>();
        Square actualSquare = knight.getSquare();

        int x = actualSquare.getX();
        int y = actualSquare.getY();

        int[][] possibleMoves = {
                {x + 2, y - 1},
                {x + 2, y + 1},
                {x - 2, y - 1},
                {x - 2, y + 1},
                {x + 1, y - 2},
                {x - 1, y - 2},
                {x + 1, y + 2},
                {x - 1, y + 2}
        };

        for (int[] move : possibleMoves) {
            int newX = move[0];
            int newY = move[1];

            if (newX < 0 || newX > 7 || newY < 0 || newY > 7) continue;

            Square square = board[newY][newX];
            if (square.getPiece() == null || square.getPiece().getPieceSide() != knight.getPieceSide()) {
                squareList.add(square);
            }
        }

        return squareList;
    }
}
