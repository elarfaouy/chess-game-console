package services;

import domain.entities.Board;
import domain.entities.Move;
import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.*;
import domain.enums.PieceSide;

import java.util.List;
import java.util.Scanner;

public class BoardService {
    private final Scanner scanner = new Scanner(System.in);
    private final Square[][] board;

    public BoardService(Board boardEntity) {
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

        if (!isValidMove(sourceSquare, targetSquare)){
            System.out.println("Invalid move. This piece cannot move to the target square.");
            return false;
        }

        sourceSquare.setPiece(null);
        targetSquare.setPiece(pieceToMove);

        // check for promoted the pawn
        if (pieceToMove instanceof Pawn && (targetSquare.getY() == 0 || targetSquare.getY() == 7)) {
            promotePawn(targetSquare);
        }

        return true;
    }

    public boolean isValidMove(Square sourceSquare, Square targetSquare) {
        Piece pieceToMove = sourceSquare.getPiece();
        List<Square> validMoves = pieceToMove.abilityMoves(board);

        return validMoves.contains(targetSquare);
    }

    private void promotePawn(Square square) {
        System.out.println("Pawn promotion! Enter the piece to promote to (Q, R, N, or B): ");
        String promotionChoice = scanner.nextLine().toUpperCase();

        Piece chosenPiece = square.getPiece();
        Piece newPiece;

        switch (promotionChoice) {
            case "Q":
                newPiece = new Queen(chosenPiece.getPieceSide());
                break;
            case "R":
                newPiece = new Rook(chosenPiece.getPieceSide());
                break;
            case "N":
                newPiece = new Knight(chosenPiece.getPieceSide());
                break;
            case "B":
                newPiece = new Bishop(chosenPiece.getPieceSide());
                break;
            default:
                System.out.println("Invalid promotion choice. Promoting to Queen by default.");
                newPiece = new Queen(chosenPiece.getPieceSide());
        }

        square.setPiece(newPiece);
    }
}
