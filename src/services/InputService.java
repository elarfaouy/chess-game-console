package services;

import domain.entities.Board;
import domain.entities.Square;

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

            if (input.length() == 2 || input.matches(inputSquareRegex)) {
                int column = input.charAt(0) - 'a';
                int row = 7 - (input.charAt(1) - '1');

                return board.getSquare(row, column);
            }

            System.out.println("Invalid input. Please enter a valid square (e.g., 'e2').");
        }
    }
}
