package domain.entities;

public class Move {
    private Square startPosition;
    private Square endPosition;
    private Piece piece;
    private Piece capturedPiece;

    public Move(Square startPosition, Square endPosition, Piece piece, Piece capturedPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
    }

    public Square getStartPosition() {
        return startPosition;
    }

    public Square getEndPosition() {
        return endPosition;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }
}
