package services;

import domain.entities.*;
import domain.enums.PieceSide;

public class GameService {
    private Game game = new Game();
    private final Board boardEntity = new Board();
    private final BoardService boardService = new BoardService(boardEntity);
    private String playerWhite;
    private String playerBlack;
    private PieceSide currentPlayer = PieceSide.WHITE;

    public void startGame() {
        // firstly, get names of player's
        playerWhite = InputService.getPlayerName("White");
        playerBlack = InputService.getPlayerName("Black");

        // set property's for the Game instant
        game.setWhitePlayer(new Player(playerWhite));
        game.setBlackPlayer(new Player(playerBlack));
        game.setBoard(boardEntity);

        inGame();
    }

    public void inGame() {
        while (true) {
            boardService.printChessboard();
            System.out.println(getCurrentPlayer() + " turn's - " + currentPlayer + "\n");

            // Get input from the current player (e.g., source and target squares)
            Square sourceSquare = InputService.getSquareInput(boardEntity);

            // validate that the source square has a piece, and it belongs to the current player
            // restart the loop if the source square is not valid
            if (!isSourceSquareValid(sourceSquare)) continue;

            Square targetSquare = InputService.getSquareInput(boardEntity);

            Move move = new Move(sourceSquare, targetSquare, sourceSquare.getPiece(), targetSquare.getPiece());
            if(!boardService.applyMove(move)) continue;

            game.addHistoryMove(move);

            // Check for game end conditions (e.g., checkmate, stalemate)
            if (isGameEnd()) {
                break;
            }

            // Switch to the next player's turn
            switchPlayer();
        }
    }

    private boolean isGameEnd() {
        // Check for game end conditions (e.g., checkmate, stalemate)
        // ...
        return false; // Modify this based on your game logic
    }

    private String getCurrentPlayer() {
        return currentPlayer.equals(PieceSide.WHITE) ? playerWhite : playerBlack;
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals(PieceSide.WHITE) ? PieceSide.BLACK : PieceSide.WHITE;
    }

    public boolean isSourceSquareValid(Square sourceSquare) {
        if (sourceSquare.getPiece() == null) {
            System.out.println("Invalid move. The source square is empty.");
            return false;
        }

        if (sourceSquare.getPiece().getPieceSide() != currentPlayer) {
            System.out.println("Invalid move. The piece on the source square does not belong to you.");
            return false;
        }

        return true;
    }
}
