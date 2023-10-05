package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;
import domain.enums.PieceSide;
import services.InputService;

import java.util.ArrayList;
import java.util.List;

public interface PawnMovementLogic {
    // Default method to calculate valid moves for a pawn
    default List<Square> getValidMoves(Square[][] board) {
        // Cast 'this' to a Piece (assuming this interface is implemented by a Pawn piece)
        Piece pawn = (Piece) this;
        // Initialize a list to store valid move destinations
        List<Square> squareList = new ArrayList<>();
        // Get the current square where the pawn is located
        Square actualSquare = pawn.getSquare();

        // Determine the direction of movement based on the pawn's side (1 for WHITE, -1 for BLACK)
        int operation = pawn.getPieceSide().equals(PieceSide.WHITE) ? 1 : -1;

        // Calculate the square in front of the pawn
        Square up = board[actualSquare.getY() - operation][actualSquare.getX()];

        // Check if the square in front of the pawn is empty
        if (up.getPiece() == null) {
            squareList.add(up); // Add the square in front to valid moves

            // Check if the pawn hasn't moved yet
            if (!pawn.isMoved()) {
                Square forward = board[actualSquare.getY() - 2 * operation][actualSquare.getX()];
                // If two squares in front of the pawn is empty, add it to valid moves
                if (forward.getPiece() == null) squareList.add(forward);
            }
        }

        // Check squares diagonally to the left and right of the pawn
        if (actualSquare.getX() > 0) {
            Square upLeft = board[actualSquare.getY() - operation][actualSquare.getX() - 1];
            if (upLeft.getPiece() != null) squareList.add(upLeft);
        }

        if (actualSquare.getX() < 7) {
            Square upRight = board[actualSquare.getY() - operation][actualSquare.getX() + 1];
            if (upRight.getPiece() != null) squareList.add(upRight);
        }

        return squareList; // Return the list of valid move destinations for the pawn
    }

    // Default method to promote a pawn to another piece when it reaches the end of the board
    default void promotePawn() {
        Piece pawn = (Piece) this;
        Piece chosenPiece = pawn.getSquare().getPiece();
        // Use InputService to get the promoted piece from the player
        Piece newPiece = InputService.getPromotePawn(chosenPiece.getPieceSide());

        // Set the pawn's square with the new promoted piece
        pawn.getSquare().setPiece(newPiece);
    }
}
