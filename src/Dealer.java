public class Dealer extends Player
{
    private final int DEALER_WEALTH = 10000;
    Dealer()
    {
        super("Dealer");
        wealth = DEALER_WEALTH;
    }

    void giveCard(Card c){
        super.hand.giveCard(c);
    }

    void takeMoney(Player p){
        this.wealth += p.getCurrentBet();
    }

    void giveMoney(int sum){
        wealth += sum;
    }

    void payBet(Player p){
        this.wealth -= 2 * p.getCurrentBet();
        p.payBet();
    }

    void giveBackBet(Player p){
        this.wealth -= 2 * p.getCurrentBet();
        p.giveBackBet();
    }


//    void giveHand(Card c1, Card c2){
//        super.hand = new Hand(c1, c2);
//    }


}
