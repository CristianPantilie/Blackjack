import java.util.*;

public class Deck
{
    enum Suit {HEARTS, SPADES, CLUBS, DIAMONDS}
    private final int DECK_CAPACITY = 52;
    private final Stack<Card> cards;
    private Stack<Card> availableCards;

    Deck()
    {
        cards = new Stack<>();
        availableCards = new Stack<>();

        for(Suit s : Suit.values()){
            for(int i = 0; i < DECK_CAPACITY / 4; i++){
                cards.add(new Card(i + 2, s.toString()));
            }
        }

        shuffleDeck();
    }

    private void shuffleDeck(){
        availableCards.clear();
        availableCards.addAll(cards);
        Collections.shuffle(availableCards);
    }

    Card popCard()
    {
        return availableCards.pop();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card c : cards){
            sb.append(c.toString()).append("\n");
        }

        return sb.toString();
    }
}
