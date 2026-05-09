import java.io.Serializable;

// Card Class provides serializable representation with validation logic
public class Card implements Serializable{

    // initalize Card Types, Colors (enum) and the Card state (instance variables)
    public enum Color {
        RED, YELLOW, GREEN, BLUE, WILD
    }

    public enum Type { // all possible TYPES of cards that can be drawn
        DRAW_TWO, WILD_FOUR, WILD, REVERSE, SKIP, NUMBER
    }

    private Color color;
    private Type type;
    private int value; // 0-9 for # cards, -1 for special type cards

    // Constructor
    public Card(Color color, Type type, int value) {
        this.color = color;
        this.type = type;
        this.value = value;
    }

    // Setters
    public void setColor(Color color){
        this.color = color;
    }
    public void setType(Type type){
        this.type = type;
    }
    public void setValue(int value){
        this.value = value;
    }
    // Getters
    public Color getColor(){
        return color;
    }
    public Type getType(){
        return type;
    }
    public int getValue(){
        return value;
    }
    // Card Validation methods
    // isNumber? or isWild? 
    public boolean isWild(){
        return type == Type.WILD || type == Type.WILD_FOUR;
    }

    // Original method (for backward compatibility if needed)
    public boolean canPlayOn(Card topCard) {
        return this.isWild() ||
            (this.color == topCard.color) ||
            (this.type == Type.NUMBER && topCard.type == Type.NUMBER && this.value == topCard.value) ||
            (this.type == topCard.type && this.type != Type.NUMBER);
    }

    // NEW: Overloaded method that considers current color (for wild cards)
    public boolean canPlayOn(Card topCard, Card.Color currentColor) {
        // Wild cards can always be played
        if (this.isWild()) {
            return true;
        }

        // If there's a current color from a wild card, check against that
        if (currentColor != null && currentColor != Card.Color.WILD) {
            if (this.color == currentColor) {
                return true;
            }
        }

        // Otherwise check against top card
        return (this.color == topCard.color) ||
               (this.type == Type.NUMBER && topCard.type == Type.NUMBER && this.value == topCard.value) ||
               (this.type == topCard.type && this.type != Type.NUMBER);
    }

    // Override toString
    // Handles string processing of Card TYPE, VAL, & NUMBER
    @Override
    public String toString() {
        if (type == Type.NUMBER) {
            return color + " " + value;
        }
        return color + " " + type;
    }
    
    public String toShortString() {
          // 1. Handle Wilds (Black buttons in GUI)
          if (type == Type.WILD) return "W";
          if (type == Type.WILD_FOUR) return "W+4";

          // 2. Extract Color Prefix (R, Y, G, B)
          String colorPrefix = (color != null) ? color.toString().substring(0, 1) : "?";

          // 3. Handle Numbers 0-9
          if (type == Type.NUMBER) {
              return colorPrefix + value; // Returns e.g., "R5", "G0"
          }
      
          // 4. Handle Special Actions
          switch (type) {
              case SKIP: return (colorPrefix + "S");
              case REVERSE: return colorPrefix + "R";
              case DRAW_TWO: return colorPrefix + "+2";
              default: return colorPrefix + "?";
        }
    }
}
