package domain.entities.pieces.movements;

import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.Pawn;
import domain.enums.PieceSide;
import services.InputService;

import java.util.ArrayList;
import java.util.List;

public interface PawnMovementLogic {
    default List<Square> getValidMoves(Square[][] board) {
        Piece pawn = (Piece) this;
        List<Square> squareList = new ArrayList<>();
        Square actualSquare = pawn.getSquare();

        int operation = pawn.getPieceSide().equals(PieceSide.WHITE) ? 1 : -1;

        Square up = board[actualSquare.getY() - operation][actualSquare.getX()];
        if (up.getPiece() == null) {
            squareList.add(up);

            if (!pawn.isMoved()) {
                Square forward = board[actualSquare.getY() - 2 * operation][actualSquare.getX()];
                if (forward.getPiece() == null) squareList.add(forward);
            }
        }

        if (actualSquare.getX() > 0) {
            Square upLeft = board[actualSquare.getY() - operation][actualSquare.getX() - 1];
            if (upLeft.getPiece() != null) squareList.add(upLeft);
        }

        if (actualSquare.getX() < 7) {
            Square upRight = board[actualSquare.getY() - operation][actualSquare.getX() + 1];
            if (upRight.getPiece() != null) squareList.add(upRight);
        }

        return squareList;
    }

    default void promotePawn() {
        Piece pawn = (Piece) this;
        Piece chosenPiece = pawn.getSquare().getPiece();
        Piece newPiece = InputService.getPromotePawn(chosenPiece.getPieceSide());

        pawn.getSquare().setPiece(newPiece);
    }

    default boolean canEnPassant(Square[][] board, Square targetSquare) {
        Piece pawn = (Piece) this;

        int axeYForEnPassant = pawn.getPieceSide().equals(PieceSide.WHITE) ? 3 : 4;
        int operation = pawn.getPieceSide().equals(PieceSide.WHITE) ? 1 : -1;

        Piece opponentPawn = board[targetSquare.getY() + operation][targetSquare.getX()].getPiece();

        return opponentPawn instanceof Pawn && (opponentPawn.getSquare().getY() == pawn.getSquare().getY()) && (pawn.getSquare().getY() == axeYForEnPassant) && ((Pawn) opponentPawn).isEnPassantVulnerable();
    }
}
