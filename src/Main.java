import java.util.Scanner;


public class Main {


    static String userInput(){
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        return str;
    }

    static Game initializeGame()
    {

        Dealer dealer = new Dealer();

        System.out.println("How many players do you want?");
        int nrPlayers = Integer.parseInt(userInput());
        Player[] players = new Player[nrPlayers];
        for(int i = 0; i < nrPlayers; i++){
            System.out.println("Player name");
            Player p = new Player(userInput());
            players[i] = p;
        }

        return new Game(dealer, players);
    }

    public static void main(String[] args)
    {
        Game g = initializeGame();
        g.play();


    }
}
