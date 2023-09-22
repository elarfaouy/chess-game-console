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
        System.out.println("waaah ina piece : ");
        String pieceToMove = scanner.nextLine();

        int row = pieceToMove.charAt(0) - 97;
        char columnChar = pieceToMove.charAt(1);
        int column = abs(Integer.parseInt(String.valueOf(columnChar)) - 8);

        Square chosenSquare = board[column][row];

        List<Square> list = chosenSquare.getPiece().abilityMoves(board);
        int i = 0;
        for (Square square : list) {
            System.out.println(i + " : " + square);
            i++;
        }

        System.out.println("waaah ina place : ");
        int place = Integer.parseInt(scanner.nextLine());

        list.get(place).setPiece(chosenSquare.getPiece());
    }
}
