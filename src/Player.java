public class Player
{
    private final String name;
    private int wealth;
    private final int INITIAL_WEALTH = 1000;

    private int currentBet;

    Hand hand;

    Player(String name){
        this.name = name;
        this.wealth = INITIAL_WEALTH;
    }

    @Override
    public String toString() {
        return name + " (" + currentBet + " points bet)";
    }

    void printNameAndWealth(){
        System.out.println(name + ": " + wealth + " points");
    }

    int getCurrentBet(){
        return currentBet;
    }

    String getName(){
        return name;
    }

    void bet(Dealer dealer, int sum){
        currentBet += sum;
        wealth -= sum;
        dealer.takeMoney(sum);
    }

    void finishRound(int wonAmount){
        currentBet = 0;
        wealth += wonAmount;
    }

    void getSplitHandMoney(int amount){
        wealth += amount;
    }

    void giveHand(Card c1, Card c2) {
        hand = new Hand(c1, c2);
    }

}
