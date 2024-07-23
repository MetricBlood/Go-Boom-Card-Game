import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Card implements Serializable {

    private String suit, rank;
    private int value;

    // Suit and rank of a standard 52-card set
    private static String[] SUIT = { "c", "d", "h", "s" };
    private static String[] RANK = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return suit + rank;
    }

    public int getCardValue() {
        if (rank.equals("10"))
            return 10;
        if (rank.equals("J"))
            return 11;
        if (rank.equals("Q"))
            return 12;
        if (rank.equals("K"))
            return 13;
        if (rank.equals("A"))
            return 14;
        else {
            int cardValue = Integer.parseInt(rank);
            return cardValue;
        }
    }

    public int getCardScore() {
        if (rank.equals("10"))
            return 10;
        else if (rank.equals("J"))
            return 10;
        else if (rank.equals("Q"))
            return 10;
        else if (rank.equals("K"))
            return 10;
        else if (rank.equals("A"))
            return 1;
        else {
            int cardValue = Integer.parseInt(rank);
            return cardValue;
        }
    }

    public static Set<Card> createFullDeck() {
        Set<Card> deck = new HashSet<>();

        for (String suit : SUIT) {
            for (String rank : RANK) {
                Card card = new Card(suit, rank);
                deck.add(card);
            }
        }

        return deck;
    }
}
