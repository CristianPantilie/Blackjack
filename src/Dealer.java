public class Dealer extends Player
{
    private final int DEALER_WEALTH = 10000;
    private int wealth;
    Dealer() {
        super("Dealer");
        wealth = DEALER_WEALTH;
    }

    void giveHand(Card c) {
        hand = new Hand(c);
    }

    void giveCard(Card c){
        super.hand.giveCard(c);
    }

    void takeMoney(int sum){
        wealth += sum;
    }

    void lostTo(Player p){
        int playerBet = p.getCurrentBet();
        this.wealth -= 2 * playerBet;
        p.finishRound(2 * playerBet);
    }

    void tiedWith(Player p){
        int playerBet = p.getCurrentBet();
        this.wealth -= playerBet;
        p.finishRound(playerBet);
    }

}
