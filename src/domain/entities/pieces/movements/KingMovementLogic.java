package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;
import java.util.ArrayList;
import java.util.List;

public interface KingMovementLogic {
    // Default method to calculate valid moves for a king
    default List<Square> getValidMoves(Square[][] board) {
        // Cast 'this' to a Piece (assuming this interface is implemented by a King piece)
        Piece king = (Piece) this;
        // Initialize a list to store valid move destinations
        List<Square> squareList = new ArrayList<>();
        // Get the current square where the king is located
        Square actualSquare = king.getSquare();

        // Extract x and y coordinates of the king's square
        int x = actualSquare.getX();
        int y = actualSquare.getY();

        // Define possible king moves (8 possible moves - horizontal, vertical, and diagonal)
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

        // Iterate over possible moves
        for (int[] move : possibleMoves) {
            int newX = move[0];
            int newY = move[1];

            // Check if the new coordinates are within the bounds of the board
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Square square = board[newY][newX];
                // Check if the square is empty or contains an opponent's piece
                // and if moving to that square doesn't result in the king being in check
                if ((square.getPiece() == null || square.getPiece().getPieceSide() != king.getPieceSide()) && !square.onCheck(board, king.getPieceSide())) {
                    squareList.add(square); // Add the square to valid moves
                }
            }
        }

        return squareList; // Return the list of valid move destinations for the king
    }

    // Default method to check if castling is possible
    default boolean canCastle(Square[][] board, Square targetSquare) {
        // Cast 'this' to a Piece (assuming this interface is implemented by a King piece)
        Piece king = (Piece) this;
        // Get the rook piece on the target square
        Piece rook = targetSquare.getPiece();

        // Check conditions for castling:
        // 1. King and rook have not moved.
        // 2. King and rook belong to the same side.
        if (king.isMoved() || rook.isMoved() || king.getPieceSide() != rook.getPieceSide()) {
            return false; // Castling is not possible
        }

        // Determine the starting and ending x-coordinates for the castling move
        int startX = Math.min(king.getSquare().getX(), rook.getSquare().getX()) + 1;
        int endX = Math.max(king.getSquare().getX(), rook.getSquare().getX());
        int y = king.getSquare().getY();

        // Check if the squares between the king and rook are empty and not under threat
        for (int i = startX; i < endX; i++) {
            // Special condition for the queen side castling
            // Check if the square near the rook is empty
            if (i == 1 && board[y][i].getPiece() != null) {
                return false; // Castling is not possible
            } else if (board[y][i].getPiece() != null || board[y][i].onCheck(board, king.getPieceSide())) {
                return false; // Castling is not possible
            }
        }

        return true; // Castling is possible
    }
}
