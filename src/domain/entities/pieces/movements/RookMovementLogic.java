package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;
import java.util.ArrayList;
import java.util.List;

public interface RookMovementLogic {
    // Default method to calculate valid moves for a rook
    default List<Square> getValidMoves(Square[][] board) {
        // Cast 'this' to a Piece (assuming this interface is implemented by a Rook piece)
        Piece rook = (Piece) this;
        // Initialize a list to store valid move destinations
        List<Square> squareList = new ArrayList<>();
        // Get the current square where the rook is located
        Square actualSquare = rook.getSquare();

        // Extract x and y coordinates of the rook's square
        int x = actualSquare.getX();
        int y = actualSquare.getY();

        // Check horizontal moves to the right
        for (int i = x + 1; i < 8; i++) {
            Square square = board[y][i];
            // If the square is empty, add it to valid moves
            if (square.getPiece() == null) {
                squareList.add(square);
            }
            // If the square contains an opponent's piece, add it and break (rook can't go further)
            else if (square.getPiece().getPieceSide() != rook.getPieceSide()) {
                squareList.add(square);
                break;
            }
            // If the square contains a piece of the same side as the rook, break (rook can't go further)
            else {
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

        return squareList; // Return the list of valid move destinations for the rook
    }
}
