import java.util.ArrayList;
import java.util.List;

public class Hand
{
    private List<Card> cards = new ArrayList<>();
    private int value;
    private int valueWithoutAce;

    private final int BUST_THRESHOLD = 22;
    private final int ACE_VALUE_11 = 11;
    private final int ACE_VALUE_1 = 1;
    private int nrAces;


    Hand(Card c1, Card c2) {
        addCard(c1);
        addCard(c2);
    }

    Hand(Card c) {
        addCard(c);
    }

    Hand() {}

    private void addCard(Card c){
        cards.add(c);
        if(c.isAce())
            nrAces++;
        updateHandValue(c);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Card c : cards){
            sb.append(c.toString()).append(", ");
        }
        return sb.toString();
    }

    Boolean isBusted(){
        return (getValue() >= BUST_THRESHOLD);
    }

    Boolean isBlackJack(){
        //you can only do a blackjack with two cards
        if(cards.size() == 2){

            return    (cards.get(0).getValue() == 10 && cards.get(1).isAce()) ||
                    (cards.get(1).getValue() == 0 && cards.get(0).isAce());


        }
        return false;
    }

    private void updateHandValue(Card c){

        if(c.isAce()){
            nrAces++;
        }
        else {
            value += c.getValue();
            valueWithoutAce += c.getValue();
        }

        for(int i = nrAces; i >0 ; i--){
            if(valueWithoutAce + ACE_VALUE_11 >= BUST_THRESHOLD){
                value = valueWithoutAce + ACE_VALUE_1;
            }
            else{
                value = valueWithoutAce + ACE_VALUE_11;
            }
        }

    }



    void giveCard(Card c){ addCard(c);}

    int getValue(){
        return this.value;
    }


    void hit(Card c){
        System.out.print("hit. ");
        addCard(c);
    }

    void stand(){
        System.out.print("stand. ");
    }

    void doubleDown(Card c){
        System.out.print("double down. ");
        addCard(c);
    }

    Card getCardIfEqual()
    {
        return this.cards.get(0);
    }

    void split()
    {
        System.out.print("split. ");
        cards.remove(1);


    }

    Boolean canSplit(){
        return (cards.size() == 2 && (cards.get(0)).equals(cards.get(1)));
    }



}
