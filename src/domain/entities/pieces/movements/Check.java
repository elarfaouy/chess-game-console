package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;
import domain.enums.PieceSide;

import java.util.List;

public interface Check {
    default boolean onCheck(Square[][] board, PieceSide side) {
        Square square = (Square) this;
        PieceSide opponentSide = (side == PieceSide.WHITE) ? PieceSide.BLACK : PieceSide.WHITE;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece opponentPiece = board[row][col].getPiece();

                if (opponentPiece != null && opponentPiece.getPieceSide() == opponentSide) {
                    List<Square> attackingSquares = opponentPiece.abilityMoves(board);

                    if (attackingSquares.contains(square)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
