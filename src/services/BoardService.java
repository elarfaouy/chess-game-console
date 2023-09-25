package services;

import domain.entities.Board;
import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.*;
import domain.enums.PieceSide;

import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

public class BoardService {
    private final Board boardEntity = new Board();
    private final Scanner scanner = new Scanner(System.in);
    private Square[][] board = boardEntity.getBoard();

    public BoardService() {
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

    public void movePawn() {
        System.out.print("Enter the square of the pawn you want to move (e.g., 'e2'): ");
        String pieceToMove = scanner.nextLine().toLowerCase();

        if (pieceToMove.length() != 2 || !pieceToMove.matches("[a-h][1-8]")) {
            System.out.println("Invalid input. Please enter a valid square (e.g., 'e2').");
            return;
        }

        int column = pieceToMove.charAt(0) - 'a';
        int row = 7 - (pieceToMove.charAt(1) - '1');

        Square chosenSquare = board[row][column];
        Piece chosenPiece = chosenSquare.getPiece();

        if (chosenPiece == null) {
            System.out.println("Invalid move. Please select a square with a piece.");
            return;
        }

        List<Square> validMoves = chosenPiece.abilityMoves(board);

        if (validMoves.isEmpty()) {
            System.out.println("No valid moves for the selected pawn.");
            return;
        }

        System.out.println("Valid moves for the selected pawn:");
        for (int i = 0; i < validMoves.size(); i++) {
            System.out.println(i + ": " + validMoves.get(i));
        }

        System.out.print("Enter the number of the move you want to make: ");
        int moveIndex = Integer.parseInt(scanner.nextLine());

        if (moveIndex < 0 || moveIndex >= validMoves.size()) {
            System.out.println("Invalid move number. Please enter a valid move number.");
            return;
        }

        Square targetSquare = validMoves.get(moveIndex);
        targetSquare.setPiece(chosenPiece);
        chosenSquare.setPiece(null);

        if (targetSquare.getY() == 0 || targetSquare.getY() == 7) {
            promotePawn(targetSquare);
        }
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
