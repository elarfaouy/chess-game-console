package services;

import domain.entities.Board;
import domain.entities.Move;
import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.*;
import domain.enums.PieceSide;

import java.util.List;

public class BoardService {
    private final Board boardEntity;
    private final Square[][] board;
    private Piece enPassantVulnerable = null;

    public BoardService(Board boardEntity) {
        this.boardEntity = boardEntity;
        this.board = boardEntity.getBoard();
        initializeBoard();
    }

    public void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = new Square(col, row);
            }
        }

        board[0][0].setPiece(new Rook(PieceSide.BLACK));
        board[0][1].setPiece(new Knight(PieceSide.BLACK));
        board[0][2].setPiece(new Bishop(PieceSide.BLACK));
        board[0][3].setPiece(new Queen(PieceSide.BLACK));
        board[0][4].setPiece(new King(PieceSide.BLACK));
        board[0][5].setPiece(new Bishop(PieceSide.BLACK));
        board[0][6].setPiece(new Knight(PieceSide.BLACK));
        board[0][7].setPiece(new Rook(PieceSide.BLACK));
        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(new Pawn(PieceSide.BLACK));
        }

        board[7][0].setPiece(new Rook(PieceSide.WHITE));
        board[7][1].setPiece(new Knight(PieceSide.WHITE));
        board[7][2].setPiece(new Bishop(PieceSide.WHITE));
        board[7][3].setPiece(new Queen(PieceSide.WHITE));
        board[7][4].setPiece(new King(PieceSide.WHITE));
        board[7][5].setPiece(new Bishop(PieceSide.WHITE));
        board[7][6].setPiece(new Knight(PieceSide.WHITE));
        board[7][7].setPiece(new Rook(PieceSide.WHITE));
        for (int i = 0; i < 8; i++) {
            board[6][i].setPiece(new Pawn(PieceSide.WHITE));
        }
    }

    public void printChessboard() {
        String[] rows = {"8", "7", "6", "5", "4", "3", "2", "1"};
        String columns = "    a     b     c    d     e    f     g     h";

        System.out.println("\n\t\t\t\t=> Chess Game <=\n");
        System.out.println(columns);

        for (int row = 0; row < 8; row++) {
            System.out.print(rows[row] + " ");
            for (int column = 0; column < 8; column++) {
                Square square = board[row][column];
                String pieceSymbol = (square.getPiece() == null) ? "    " : square.getPiece().getSymbol();

                // Color the squares
                String bgColor = (row + column) % 2 == 0 ? "\u001B[48;5;240m" : "\u001B[48;5;235m";
                String resetColor = "\u001B[0m";

                System.out.print(bgColor + "[" + pieceSymbol + "]" + resetColor);
            }

            System.out.println(" " + rows[row]);
        }

        System.out.println(columns);
    }

    public boolean applyMove(Move move) {
        Square sourceSquare = move.getStartPosition();
        Square targetSquare = move.getEndPosition();
        Piece pieceToMove = move.getPiece();
        Piece capturedPiece = move.getCapturedPiece();

        if (!isValidMove(sourceSquare, targetSquare)) {

            // check for castling
            if (pieceToMove instanceof King && capturedPiece instanceof Rook) {
                King king = (King) pieceToMove;

                if (king.canCastle(board, targetSquare)) {
                    int isCastleQueenSide = targetSquare.getX() == 0 ? -1 : 1;
                    board[sourceSquare.getY()][sourceSquare.getX() + 2 * isCastleQueenSide].setPiece(king);
                    sourceSquare.setPiece(null);
                    king.setMoved(true);

                    board[sourceSquare.getY()][sourceSquare.getX() + isCastleQueenSide].setPiece(capturedPiece);
                    targetSquare.setPiece(null);
                    capturedPiece.setMoved(true);

                    return true;
                }
            }

            // check for en passant
            boolean isTargetInRangeForEnPassant = Math.abs(sourceSquare.getX() - targetSquare.getX()) == 1 && Math.abs(sourceSquare.getY() - targetSquare.getY()) == 1;
            if (pieceToMove instanceof Pawn && capturedPiece == null && isTargetInRangeForEnPassant) {
                int operation = pieceToMove.getPieceSide().equals(PieceSide.WHITE) ? 1 : -1;
                Piece opponentPawn = board[targetSquare.getY() + operation][targetSquare.getX()].getPiece();

                if (opponentPawn == enPassantVulnerable) {
                    sourceSquare.setPiece(null);
                    pieceToMove.setMoved(true);
                    targetSquare.setPiece(pieceToMove);

                    opponentPawn.getSquare().setPiece(null);

                    return true;
                }
            }

            System.out.println("Invalid move. This piece cannot move to the target square.");
            return false;
        }

        // here for is pawn in en passant state
        if (pieceToMove instanceof Pawn && !pieceToMove.isMoved() && Math.abs(sourceSquare.getY() - targetSquare.getY()) == 2) {
            enPassantVulnerable = pieceToMove;
        } else {
            enPassantVulnerable = null;
        }

        // check for promoted the pawn
        if (pieceToMove instanceof Pawn && (targetSquare.getY() == 0 || targetSquare.getY() == 7)) {
            ((Pawn) pieceToMove).promotePawn();
        }

        sourceSquare.setPiece(null);
        pieceToMove.setMoved(true);
        targetSquare.setPiece(pieceToMove);

        Square kingSquare = boardEntity.findKing(pieceToMove.getPieceSide());
        if (kingSquare.onCheck(board, pieceToMove.getPieceSide())){
            System.out.println("you are in check state !!!");

            sourceSquare.setPiece(pieceToMove);
            pieceToMove.setMoved(false);
            targetSquare.setPiece(capturedPiece);
            return false;
        }

        return true;
    }

    public boolean isValidMove(Square sourceSquare, Square targetSquare) {
        Piece pieceToMove = sourceSquare.getPiece();
        List<Square> validMoves = pieceToMove.abilityMoves(board);

        return validMoves.contains(targetSquare);
    }
}
