package uno;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class UnoDeck {

    private UnoCard[] cards;
    private int cardsInDeck;

    public UnoDeck(){
        cards = new UnoCard[108];
        reset();
        shuffle();
    }

    public void reset() {

    UnoCard.Color[] colors = UnoCard.Color.values();
    cardsInDeck = 0;

    // 1. Handle Colored Cards (Red, Green, Blue, Yellow)
    for (int i = 0; i < colors.length - 1; i++) {
        UnoCard.Color color = colors[i];

        // One card of value 0 per color
        cards[cardsInDeck++] = new UnoCard(color, UnoCard.Value.getValue(0));

        // Two sets of 1-9
        for (int j = 1; j <= 9; j++) {
            cards[cardsInDeck++] = new UnoCard(color, UnoCard.Value.getValue(j));
            cards[cardsInDeck++] = new UnoCard(color, UnoCard.Value.getValue(j));
        }

        // Two of each Action Card per color
        UnoCard.Value[] values = new UnoCard.Value[]{UnoCard.Value.DrawTwo, UnoCard.Value.Skip, UnoCard.Value.Reverse};
        for (UnoCard.Value value : values) {
            cards[cardsInDeck++] = new UnoCard(color, value);
            cards[cardsInDeck++] = new UnoCard(color, value);
        }
    }

    // 2. Handle Wild Cards (Outside the color loop)
    // 4 Wilds and 4 WildFours
    UnoCard.Value[] values = new UnoCard.Value[]{UnoCard.Value.Wild, UnoCard.Value.WildFour};
    for (int i = 0; i < 4; i++) {
        cards[cardsInDeck++] = new UnoCard(UnoCard.Color.Wild, UnoCard.Value.Wild);
        cards[cardsInDeck++] = new UnoCard(UnoCard.Color.Wild, UnoCard.Value.WildFour);
    }
}
    public void replaceDeckWith(ArrayList<UnoCard> cards) {
        this.cards = cards.toArray(new UnoCard(cards.size()));
        this.cardsInDeck = this.cards.length;
    }

    public boolean isEmpty() {
        return cardsInDeck == 0;
    }

    public void shuffle() {
        int n = cards.length;
        Random random = new Random();

        for(int i = 0; i < n; i++){
            // get a random index of the array past the current infex
            // The argument is an exclusive bound
            // Swap the random element with the previous element

            int randomValue = i + random.nextInt(n - i);
            UnoCard randomCard = cards[randomValue];
            cards[randomValue] = cards[i];
;           cards[i] = randomCard;;
        }
    }
    public UnoCard drawCard() throws IllegalArgumentException {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot draw a card since there are no cards in the deck.");
        }
        return cards[--cardsInDeck];
    }

    public ImageIcon drawCardImage() throws IllegalArgumentException {
        if(isEmpty()) {
            throw new IllegalArgumentException("Cannot draw a card since the deck is empty");
        }
        return new ImageIcon(cards[--cardsInDeck].toString() + ".png"); 
    }

    public UnoCard[] drawCard(int n) {
        if (n < 0){
            throw new IllegalArgumentException("Must draw positive cards but tried to draw" + n + " careds.");
        }

        if (n > cardsInDeck){
            throw new IllegalArgumentException("Cannot draw" + n + " cards since there are only " + cardsInDeck + " cards");
        }

        UnoCard[] ret = new UnoCard[n];

        for (int i = 0; i < n; i++) {
            ret[i] = cards[--cardsInDeck];
        }
        return ret;
    }
}
