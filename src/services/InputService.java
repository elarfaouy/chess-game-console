package services;

import domain.entities.Board;
import domain.entities.Piece;
import domain.entities.Square;
import domain.entities.pieces.Bishop;
import domain.entities.pieces.Knight;
import domain.entities.pieces.Queen;
import domain.entities.pieces.Rook;
import domain.enums.PieceSide;

import java.util.Scanner;

public class InputService {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String namePlayerRegex = "^(?=.{3,10}$)[A-Za-z]+(?:\\s[A-Za-z]+)?$";
    private static final String inputSquareRegex = "[a-h][1-8]";

    public static String getPlayerName(String player) {
        while (true) {
            System.out.print("Enter player " + player + " name: ");
            String name = scanner.nextLine();

            if (name.matches(namePlayerRegex)) {
                return name;
            }

            System.out.println("\nThe player's name is not valid should be string and between 3, 10 chars !!!");
        }
    }

    public static Square getSquareInput(Board board) {
        while (true) {
            System.out.print("Enter the square (e.g., 'e2'): ");
            String input = scanner.nextLine().toLowerCase();

            if (input.length() == 2 && input.matches(inputSquareRegex)) {
                int column = input.charAt(0) - 'a';
                int row = 7 - (input.charAt(1) - '1');

                return board.getSquare(row, column);
            }

            System.out.println("Invalid input. Please enter a valid square (e.g., 'e2').");
        }
    }

    public static Piece getPromotePawn(PieceSide side) {
        System.out.print("Pawn promotion! Enter the piece to promote to (Q, R, N, or B): ");
        String promotionChoice = scanner.nextLine().toUpperCase();

        switch (promotionChoice) {
            case "Q":
                return new Queen(side);
            case "R":
                return new Rook(side);
            case "N":
                return new Knight(side);
            case "B":
                return new Bishop(side);
            default:
                System.out.println("Invalid promotion choice. Promoting to Queen by default.");
                return new Queen(side);
        }
    }
}
