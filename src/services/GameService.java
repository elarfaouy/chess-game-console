package services;

import domain.entities.*;
import domain.enums.GameResult;
import domain.enums.PieceSide;

import java.util.List;

public class GameService {
    private final Game game = new Game();
    private final Board boardEntity = new Board();
    private final BoardService boardService = new BoardService(boardEntity);
    private String playerWhite = "hicham"; // todo : just for testing after that should remove initialization
    private String playerBlack = "hamza";
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
            if (!boardService.applyMove(move)) continue;

            game.addHistoryMove(move);

            // Check for game end conditions (e.g., checkmate, stalemate)
            if (isGameEnd()) {
                System.out.println(getCurrentPlayer() + " WIN");
                break;
            }

            // Switch to the next player's turn
            switchPlayer();
        }
    }

    private boolean isGameEnd() {
        PieceSide opponentSide = (currentPlayer == PieceSide.WHITE) ? PieceSide.BLACK : PieceSide.WHITE;
        Square[][] board = boardEntity.getBoard();

        if (isCheckmate(board, opponentSide)){
            System.out.println("Checkmate");
            game.setResult(currentPlayer.equals(PieceSide.WHITE) ? GameResult.WHITE_WIN : GameResult.BLACK_WIN);
            return true;
        } else if (isStalemate(board, opponentSide)){
            System.out.println("Stalemate");
            game.setResult(GameResult.DRAW);
            return true;
        }

        return false;
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

    public boolean isCheckmate(Square[][] board, PieceSide side) {
        Square opponentKingSquare = boardEntity.findKing(side);

        if (!opponentKingSquare.onCheck(board, side)) {
            return false;
        }

        // Check if any move by the opponent can get out of check
        return isCurrentPlayerHasLegalMoves(board, side, opponentKingSquare);
    }

    private boolean isStalemate(Square[][] board, PieceSide side) {
        Square opponentKingSquare = boardEntity.findKing(side);

        if (opponentKingSquare.onCheck(board, side)) {
            return false;
        }

        // Check for stalemate
        return isCurrentPlayerHasLegalMoves(board, side, opponentKingSquare);
    }

    private boolean isCurrentPlayerHasLegalMoves(Square[][] board, PieceSide side, Square opponentKingSquare) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square sourceSquare = boardEntity.getSquare(row, col);
                Piece piece = sourceSquare.getPiece();

                if (piece != null && piece.getPieceSide() == side) {
                    List<Square> validMoves = piece.abilityMoves(board);

                    for (Square targetSquare : validMoves) {
                        // make move
                        Piece capturedPiece = targetSquare.getPiece();
                        targetSquare.setPiece(piece);
                        sourceSquare.setPiece(null);

                        boolean inCheck = opponentKingSquare.onCheck(board, side);

                        // undo move
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
