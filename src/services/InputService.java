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

    // Method to get a valid player name
    public static String getPlayerName(String player) {
        while (true) {
            System.out.print("Enter player " + player + " name: ");
            String name = scanner.nextLine();

            // Check if the name matches the specified regex pattern
            if (name.matches(namePlayerRegex)) {
                return name;
            }

            System.out.println("\nThe player's name is not valid; it should be a string between 3 and 10 characters.");
        }
    }

    // Method to get valid square input (e.g., 'e2')
    public static Square getSquareInput(Board board) {
        while (true) {
            System.out.print("Enter the square (e.g., 'e2'): ");
            String input = scanner.nextLine().toLowerCase();

            // Check if the input matches the specified regex pattern and has a valid format
            if (input.length() == 2 && input.matches(inputSquareRegex)) {
                int column = input.charAt(0) - 'a';
                int row = 7 - (input.charAt(1) - '1');

                // Get the corresponding square from the board
                return board.getSquare(row, column);
            }

            System.out.println("Invalid input. Please enter a valid square (e.g., 'e2').");
        }
    }

    // Method to handle pawn promotion
    public static Piece getPromotePawn(PieceSide side) {
        System.out.print("Pawn promotion! Enter the piece to promote to (Q, R, N, or B): ");
        String promotionChoice = scanner.nextLine().toUpperCase();

        // Based on the player's choice, return the corresponding promoted piece
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
