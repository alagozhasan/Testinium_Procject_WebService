package Models;

public class Card {
    private String cardID;
    public static final String cardUrl = "cards";

    private String cardName;

    public Card(String cardName) {
        this.cardName = cardName;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }


    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
