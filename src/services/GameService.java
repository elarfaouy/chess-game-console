package services;

import domain.entities.*;
import domain.enums.GameResult;
import domain.enums.PieceSide;

import java.util.List;

public class GameService {
    private final Game game = new Game();
    private final Board boardEntity = new Board();
    private final BoardService boardService = new BoardService(boardEntity);
    private String playerWhite;
    private String playerBlack;
    private PieceSide currentPlayer = PieceSide.WHITE;

    // Method to start the game
    public void startGame() {
        // Get the names of players
        playerWhite = InputService.getPlayerName("White");
        playerBlack = InputService.getPlayerName("Black");

        // Set properties for the Game instance
        game.setWhitePlayer(new Player(playerWhite));
        game.setBlackPlayer(new Player(playerBlack));
        game.setBoard(boardEntity);

        // Start the game loop
        inGame();
    }

    // Main game loop
    public void inGame() {
        while (true) {
            boardService.printChessboard();
            System.out.println(getCurrentPlayer() + "'s turn - " + currentPlayer + "\n");

            // Get input from the current player (e.g., source and target squares)
            Square sourceSquare = InputService.getSquareInput(boardEntity);

            // Validate that the source square has a piece and it belongs to the current player
            if (!isSourceSquareValid(sourceSquare)) {
                System.out.println("Invalid move. This piece cannot move to the target square.");
                continue;
            }

            Square targetSquare = InputService.getSquareInput(boardEntity);

            // Create a move object and apply the move to the board
            Move move = new Move(sourceSquare, targetSquare, sourceSquare.getPiece(), targetSquare.getPiece());
            if (!boardService.applyMove(move)) {
                System.out.println("Invalid move. Please try again.");
                continue;
            }

            // Add the move to the game's move history
            game.addHistoryMove(move);

            // Check for game end conditions (e.g., checkmate, stalemate)
            if (isGameEnd()) {
                break;
            }

            // Switch to the next player's turn
            switchPlayer();
        }
    }

    // Check if the game has ended
    private boolean isGameEnd() {
        PieceSide opponentSide = (currentPlayer == PieceSide.WHITE) ? PieceSide.BLACK : PieceSide.WHITE;
        Square[][] board = boardEntity.getBoard();

        if (isCheckmate(board, opponentSide)) {
            System.out.println("Checkmate! " + getCurrentPlayer() + " wins the game!");
            game.setResult(currentPlayer.equals(PieceSide.WHITE) ? GameResult.WHITE_WIN : GameResult.BLACK_WIN);
            return true;
        } else if (isStalemate(board, opponentSide)) {
            System.out.println("Stalemate! The game ends in a draw.");
            game.setResult(GameResult.DRAW);
            return true;
        }

        return false;
    }

    // Get the name of the current player
    private String getCurrentPlayer() {
        return currentPlayer.equals(PieceSide.WHITE) ? playerWhite : playerBlack;
    }

    // Switch to the next player's turn
    private void switchPlayer() {
        currentPlayer = currentPlayer.equals(PieceSide.WHITE) ? PieceSide.BLACK : PieceSide.WHITE;
    }

    // Check if the source square is valid (contains a piece and belongs to the current player)
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

    // Check if the opponent is in checkmate
    public boolean isCheckmate(Square[][] board, PieceSide side) {
        Square opponentKingSquare = boardEntity.findKing(side);

        if (!opponentKingSquare.onCheck(board, side)) {
            return false;
        }

        // Check if any move by the opponent can get out of check
        return isCurrentPlayerHasLegalMoves(board, side);
    }

    // Check if the game is in stalemate
    private boolean isStalemate(Square[][] board, PieceSide side) {
        Square opponentKingSquare = boardEntity.findKing(side);

        if (opponentKingSquare.onCheck(board, side)) {
            return false;
        }

        // Check for stalemate
        return isCurrentPlayerHasLegalMoves(board, side);
    }

    // Check if the current player has legal moves to make
    private boolean isCurrentPlayerHasLegalMoves(Square[][] board, PieceSide side) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square sourceSquare = boardEntity.getSquare(row, col);
                Piece piece = sourceSquare.getPiece();

                if (piece != null && piece.getPieceSide() == side) {
                    List<Square> validMoves = piece.abilityMoves(board);

                    for (Square targetSquare : validMoves) {
                        // Make the move
                        Piece capturedPiece = targetSquare.getPiece();
                        targetSquare.setPiece(piece);
                        sourceSquare.setPiece(null);

                        boolean inCheck = boardEntity.findKing(side).onCheck(board, side);

                        // Undo the move
                        sourceSquare.setPiece(piece);
                        targetSquare.setPiece(capturedPiece);

                        if (!inCheck) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
