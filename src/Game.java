import java.util.*;

public class Game
{

    private final int DEALER_STAND_THRESHOLD = 17;
    private Collection<Player> players = new ArrayList<>();
    private Dealer dealer;
    private Deck deck;

    enum Option {HIT, STAND, DOUBLEDOWN, SPLIT}

    Game(Dealer dealer, Player... player){
        players.addAll(Arrays.asList(player));
        this.dealer = dealer;
        deck =  new Deck();
    }


    void play()
    {

        while(true){
            System.out.println("***************************");
            for(Player p : players)
                p.printNameAndWealth();
            System.out.println("***************************");
            round();
            deck.shuffleDeck();
        }
    }

    private String currentSituation(Collection<Player> players){
        StringBuilder sb = new StringBuilder();
        for(Player p : players){
            sb.append(p.toString()).append(": ").append(p.hand.toString()).append("\n");
        }
        return sb.toString();
    }

    private boolean checkBlackJack(Collection<Player> players){
        Collection<Player> playersWithBlackJack = new ArrayList<>();
        boolean foundBlackJack = false;
        for(Player p : players){
            if(p.hand.isBlackJack()) {
                playersWithBlackJack.add(p);
                foundBlackJack = true;
            }
        }

        if(foundBlackJack) {
            players.clear();
            players.addAll(playersWithBlackJack);
        }

        return foundBlackJack;
    }

    private boolean hitOrStandLoop(Player player, Hand hand){
        //returns false if the player busts and true if he doesn't
        boolean again = true;

        while(again)
        {
            hand.giveCard(deck.popCard());
            System.out.println("His hand is now: " + hand.toString());
            if(hand.isBusted())
            {
                System.out.println(player.getName() + " you busted!");
                return false;
            }
            else
            {
                System.out.println(player.getName() + " would you like to hit again?(Y/N)");
                String option = Main.userInput().toUpperCase();
                if (option.equals("Y"))
                {
                    System.out.println(player.getName() + " chose to hit again");
                    again = true;
                }
                else
                {
                    System.out.println(player.getName() + " chose to stand");
                    again = false;
                }
            }
        }
        return true;
    }

    private boolean doubleDown(Player p){
        p.bet(dealer, p.getCurrentBet());
        p.hand.giveCard(deck.popCard());
        System.out.println("Double down");
        System.out.println("His hand is now: " + p.hand.toString() +
                            "and his bet is: " + p.getCurrentBet());

        return p.hand.isBusted();

    }

    private void split(Player p, ListIterator<Player> it)
    {
        if (p.hand.canSplit()) {
            System.out.println("split. ");
            //take a card away from the current hand
            p.hand.split();
            //make a new player with identical bet
            Player clone = new SplitPlayer(p);
            clone.bet(dealer, p.getCurrentBet());

            //hit for each of them
            p.hand.giveCard(deck.popCard());
            clone.hand.giveCard(deck.popCard());

            System.out.println(p.getName() + "split. His new hands are: ");
            System.out.println(p.hand.toString());
            System.out.println(clone.hand.toString());

            //add the clone to the list and set the iterator back to ask the current player and his clone for options again
            it.add(clone);
            it.previous();
            it.previous();

        } else
            System.out.println("Can't split, cards aren't equal");
    }

    private void takeBets(List<Player> players)
    {
        for(Player p : players){
            System.out.println(p.getName() + ": place your bet.");
            int bet = Integer.parseInt(Main.userInput());
            p.bet(dealer, bet);
        }
    }

    private void giveInitialCards(List<Player> players){
        for(Player p : players){
            p.giveHand(deck.popCard(), deck.popCard());
        }

        //and one to the dealer
        dealer.giveHand(deck.popCard());
    }

    private void getPlayerOptions(List<Player> players)
    {
        ListIterator<Player> it = players.listIterator();

        while(it.hasNext())
        {
            Player p = it.next();
            System.out.println(p.toString() + ", what is your option? (HIT, SPLIT, STAND, DOUBLEDOWN)");
            Option op = Option.valueOf(Main.userInput().toUpperCase());
            System.out.print(p.getName() + " chose to ");
            switch (op) {
                case HIT:
                    //if the player busts after the hit he is removed from the winners list
                    if (!hitOrStandLoop(p, p.hand)) {
                        p.finishRound(0);
                        it.remove();
                    }
                    break;
                case SPLIT:
                    split(p, it);
                    break;
                case STAND:
                    System.out.println("stand.");
                    break;
                case DOUBLEDOWN:
                    if(!doubleDown(p)){
                        p.finishRound(0);
                        it.remove();
                    }
                    break;
                default:
                    System.out.println("Please select a valid option.");
                    break;
            }
        }
    }

    private boolean dealerHasBlackJack(List<Player> players, boolean playerHasBlackJack){
        if(dealer.hand.isBlackJack())
        {
            System.out.println("Dealer blackjacked!");
            if(playerHasBlackJack){
                System.out.print("Tie with: ");
                for(Player p : players) {
                    System.out.print(p.getName() + " ");
                }
            }
            else{
                System.out.println("There are no other blackjacks. Dealer wins.");
            }

            return true;
        }
        return false;
    }

    private boolean dealerCompleteDrawing(List<Player> players){
        while(dealer.hand.getValue() < DEALER_STAND_THRESHOLD)
        {
            System.out.println("Dealer hits.");
            dealer.hand.giveCard(deck.popCard());
            System.out.println("Dealer's hand is: " + dealer.hand.toString());
            if(dealer.hand.isBusted())
            {
                System.out.print("Dealer busted! ");
                for(Player p : players){
                    System.out.print(p.getName() + " ");
                    dealer.lostTo(p);
                }
                System.out.println("win.");
                return true;
            }
        }
        return false;
    }

    private void calculateWinners(List<Player> players){
        int dealerValue = dealer.hand.getValue();
        for(Player p : players)
        {
            if(p.hand.getValue() > dealerValue){
                System.out.println(p.getName() + " wins. He receives " + p.getCurrentBet() + ". ");
                dealer.lostTo(p);
            }
            else if(p.hand.getValue() == dealerValue){
                System.out.println(p.getName() + " is at a tie with the dealer. His bet is given back.");
                dealer.tiedWith(p);
            }
            else{
                System.out.println(p.getName() + " loses. He loses his bet. ");
            }

            if(p instanceof SplitPlayer){
                ((SplitPlayer) p).yieldWinnings();
            }
        }
    }

    private void round()
    {

        List<Player> remainingPlayers = new ArrayList<>(this.players);

        takeBets(remainingPlayers);

        giveInitialCards(remainingPlayers);

        System.out.println("Dealer: " + dealer.hand.toString());
        System.out.println(currentSituation(players));

        boolean playerHasBlackJack = checkBlackJack(remainingPlayers);

        if(!playerHasBlackJack){
            getPlayerOptions(remainingPlayers);
        }


        dealer.giveCard(deck.popCard());
        System.out.println("Dealer's hand is: " + dealer.hand.toString());


        if(dealerHasBlackJack(remainingPlayers, playerHasBlackJack))
            return; //round finished


        if(dealerCompleteDrawing(remainingPlayers))  //dealer draws his cards
            return; //if dealer has busted, round finishes


        calculateWinners(remainingPlayers);
    }


}
