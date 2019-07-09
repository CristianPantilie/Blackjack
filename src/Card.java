public class Card
{
    private final int number;
    private final String suit;
    private final int value;

    Card(int number, String suit)
    {
        this.number = number;
        this.suit = suit;

        if(number <= 10)
            value = number;
        else if(number == 11)
            value = -1; // ace, depends on the other cards in the hand
        else
            value = 10;
    }

    int getValue(){
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        Card c = (Card) obj;
        return (this.number == c.number);
    }

    Boolean isAce(){
        return (number == 11);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        switch (number){
            case 11:
                ret.append("Ace");
                break;
            case 12:
                ret.append("Jack");
                break;
            case 13:
                ret.append("Queen");
                break;
            case 14:
                ret.append("King");
                break;
            default:
                ret.append(number);
                break;

        }
        ret.append(" of ").append(suit.toLowerCase()).append(" ");
        return ret.toString();
    }
}
