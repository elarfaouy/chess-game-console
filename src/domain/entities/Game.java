package domain.entities;

import domain.enums.GameResult;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private GameResult result;
    private List<Move> historyMoves = new ArrayList<>();

    public Game() {
    }

    public Game(Board board, Player whitePlayer, Player blackPlayer, GameResult result) {
        this.board = board;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.result = result;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    public List<Move> getHistoryMoves() {
        return historyMoves;
    }

    public void setHistoryMoves(List<Move> historyMoves) {
        this.historyMoves = historyMoves;
    }

    public void addHistoryMove(Move move) {
        this.historyMoves.add(move);
    }
}
