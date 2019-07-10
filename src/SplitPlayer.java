public class SplitPlayer extends Player
{
    private Player originalPlayer;

    private int amountLost;
    private int amountWon;

    SplitPlayer(Player originalPlayer){
        super(originalPlayer.getName() + " (other hand)");
        this.originalPlayer = originalPlayer;

        amountLost = originalPlayer.getCurrentBet();
        amountWon = 0;

        hand = new Hand(originalPlayer.hand.getCardIfEqual());
    }

    void yieldWinnings() {
        originalPlayer.getSplitHandMoney(-amountLost);
        originalPlayer.getSplitHandMoney(amountWon);
    }

    void finishRound(int wonAmount){
        amountWon = wonAmount;
    }
}
