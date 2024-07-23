import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public class Deck implements Serializable {
    private List<Card> cards;

    // Suit and rank of a standard 52-card deck
    private static String[] SUIT = { "c", "d", "h", "s" };
    private static String[] RANK = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };

    //initialize Deck with 52 cards
    public Deck() {
        this.cards = new ArrayList<>();
        for (String suit : SUIT) {
            for (String rank : RANK) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    //list out all cards that are currently in the deck
    public void showDeck() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            sb.append(cards.get(i));
            if (i != cards.size() - 1) {
                sb.append(", ");
            }
        }
        System.out.print("[" + sb + "]");
    }


    //shuffle cards in the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    //draw a card
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }

    public int getDeckSize() {
        return cards.size();
    }

    public void setCards(List<Card> newCards) {
        cards = new ArrayList<>(newCards);
    }
}
