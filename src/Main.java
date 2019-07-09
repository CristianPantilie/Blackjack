import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

//    private void playerBox(Group group){
//        Box box = new Box(200.0f, 120.0f, 150.0f);
//        box.setTranslateX(100);
//        box.setTranslateY(100);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception
//    {
//
//        Button addPlayerButton = new Button();
//
//        Group root = new Group();
//
//        addPlayerButton.setText("Add a player");
//        addPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                playerBox(root);
//            }
//        });
//
//
//        //StackPane root = new StackPane();
//        root.getChildren().add(addPlayerButton);
//
//
//        Scene scene = new Scene(root, 800, 600);
//        primaryStage.setTitle("Blackjack");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

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
