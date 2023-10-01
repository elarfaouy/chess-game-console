import services.BoardService;
import services.GameService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        GameService gameService = new GameService();
        gameService.inGame();
    }
}