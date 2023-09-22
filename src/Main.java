import services.BoardService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        BoardService boardService = new BoardService();
        boardService.printChessboard();
        boardService.movePawn();
        boardService.printChessboard();
    }
}