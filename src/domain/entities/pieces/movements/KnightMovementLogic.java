package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;
import java.util.ArrayList;
import java.util.List;

public interface KnightMovementLogic {
    // Default method to calculate valid moves for a knight
    default List<Square> getValidMoves(Square[][] board) {
        // Cast 'this' to a Piece (assuming this interface is implemented by a Knight piece)
        Piece knight = (Piece) this;
        // Initialize a list to store valid move destinations
        List<Square> squareList = new ArrayList<>();
        // Get the current square where the knight is located
        Square actualSquare = knight.getSquare();

        // Extract x and y coordinates of the knight's square
        int x = actualSquare.getX();
        int y = actualSquare.getY();

        // Define possible knight moves (8 possible moves in an L-shape)
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

        // Iterate over possible moves
        for (int[] move : possibleMoves) {
            int newX = move[0];
            int newY = move[1];

            // Check if the new coordinates are within the bounds of the board
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Square square = board[newY][newX];
                // Check if the square is empty or contains an opponent's piece
                if (square.getPiece() == null || square.getPiece().getPieceSide() != knight.getPieceSide()) {
                    squareList.add(square); // Add the square to valid moves
                }
            }
        }

        return squareList; // Return the list of valid move destinations for the knight
    }
}
