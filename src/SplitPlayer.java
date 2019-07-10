public class SplitPlayer extends Player
{
    private Player originalPlayer;

    private int amountLost;
    private int amountWon;

    SplitPlayer(Player originalPlayer){
        super(originalPlayer.getName() + " (other hand)");
        this.bet(originalPlayer.getCurrentBet());

        amountLost = originalPlayer.getCurrentBet();
        amountWon = 0;

        hand = new Hand(originalPlayer.hand.getCardIfEqual());
    }

    void yieldWinnings() {
        originalPlayer.getSplitHandMoney(-amountLost);
        originalPlayer.getSplitHandMoney(amountWon);
    }

    void payBet(){
        amountLost = 0;
        amountWon += getCurrentBet();
    }

    void giveBackBet(){
        amountWon = 0;
        amountLost = 0;
    }



}
