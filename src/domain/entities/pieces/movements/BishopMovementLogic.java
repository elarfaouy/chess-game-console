package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;
import java.util.ArrayList;
import java.util.List;

public interface BishopMovementLogic {
    // Default method to calculate valid moves for a bishop
    default List<Square> getValidMoves(Square[][] board) {
        // Cast 'this' to a Piece (assuming this interface is implemented by a Piece)
        Piece bishop = (Piece) this;
        // Initialize a list to store valid move destinations
        List<Square> squareList = new ArrayList<>();
        // Get the current square where the bishop is located
        Square actualSquare = bishop.getSquare();

        // Extract x and y coordinates of the bishop's square
        int x = actualSquare.getX();
        int y = actualSquare.getY();

        // Check diagonal moves to the top-right
        for (int i = 1; x + i < 8 && y - i >= 0; i++) {
            Square square = board[y - i][x + i];
            if (square.getPiece() == null) {
                squareList.add(square); // Add empty square to valid moves
            } else if (square.getPiece().getPieceSide() != bishop.getPieceSide()) {
                squareList.add(square); // Add square with an opponent's piece to valid moves
                break; // Bishop can't move beyond opponent's piece
            } else {
                break; // Bishop can't move beyond its own piece
            }
        }

        // Check diagonal moves to the top-left
        for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
            Square square = board[y - i][x - i];
            if (square.getPiece() == null) {
                squareList.add(square); // Add empty square to valid moves
            } else if (square.getPiece().getPieceSide() != bishop.getPieceSide()) {
                squareList.add(square); // Add square with an opponent's piece to valid moves
                break; // Bishop can't move beyond opponent's piece
            } else {
                break; // Bishop can't move beyond its own piece
            }
        }

        // Check diagonal moves to the bottom-right
        for (int i = 1; x + i < 8 && y + i < 8; i++) {
            Square square = board[y + i][x + i];
            if (square.getPiece() == null) {
                squareList.add(square); // Add empty square to valid moves
            } else if (square.getPiece().getPieceSide() != bishop.getPieceSide()) {
                squareList.add(square); // Add square with an opponent's piece to valid moves
                break; // Bishop can't move beyond opponent's piece
            } else {
                break; // Bishop can't move beyond its own piece
            }
        }

        // Check diagonal moves to the bottom-left
        for (int i = 1; x - i >= 0 && y + i < 8; i++) {
            Square square = board[y + i][x - i];
            if (square.getPiece() == null) {
                squareList.add(square); // Add empty square to valid moves
            } else if (square.getPiece().getPieceSide() != bishop.getPieceSide()) {
                squareList.add(square); // Add square with an opponent's piece to valid moves
                break; // Bishop can't move beyond opponent's piece
            } else {
                break; // Bishop can't move beyond its own piece
            }
        }

        return squareList; // Return the list of valid move destinations
    }
}
