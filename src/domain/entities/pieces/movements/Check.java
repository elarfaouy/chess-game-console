package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.King;
import domain.enums.PieceSide;

import java.util.List;

public interface Check {
    // Default method to check if a square is under threat (in check)
    default boolean onCheck(Square[][] board, PieceSide side) {
        // Cast 'this' to a Square (assuming this interface is implemented by a Square)
        Square square = (Square) this;

        // Determine the opponent's side
        PieceSide opponentSide = (side == PieceSide.WHITE) ? PieceSide.BLACK : PieceSide.WHITE;

        // Loop through all squares on the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Get the piece on the current square
                Piece opponentPiece = board[row][col].getPiece();

                // Check if there is an opponent's piece (not null) and it's not a King
                if (opponentPiece != null && opponentPiece.getPieceSide() == opponentSide && !(opponentPiece instanceof King)) {
                    // Get the list of squares that the opponent's piece can attack
                    List<Square> attackingSquares = opponentPiece.abilityMoves(board);

                    // Check if the square is in the list of attacking squares
                    if (attackingSquares.contains(square)) {
                        return true; // The square is under threat (in check)
                    }
                }
            }
        }

        return false; // The square is not under threat (not in check)
    }
}
