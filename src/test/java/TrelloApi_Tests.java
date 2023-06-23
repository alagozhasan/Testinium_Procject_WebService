import Models.Board;
import Models.Card;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.*;

public class TrelloApi_Tests {
    static Board board = new Board("TestBoard");
    static Card cardTest1 = new Card("cardTest1");
    static Card cardTest2 = new Card("cardTest2");

    static String API_KEY = "ab96baa21a9c19f06595cb589b2838f3";
    static String API_TOKEN = "ATTA494b17edc07bc6e07a9a33e86d7cfa8c63bee7b1e372c090bce0fdfe2e78d29aAFE9CC54";
    static String baseURI = "https://api.trello.com/1/";

    static RequestSpecification reqSpec = new RequestSpecBuilder()
            .addHeader("Accept", "application/json")
            .setContentType(ContentType.JSON)
            .setBaseUri(baseURI)

            .addQueryParam("key", API_KEY)
            .addQueryParam("token", API_TOKEN)
            .build();

    public static void main(String[] args) {


        createBoard();

        createCard(cardTest1);
        createCard(cardTest2);

        int choice = getRandom(2);

        if (choice == 0) {
            updateCard(cardTest1);
        } else {
            updateCard(cardTest2);
        }
        for (Card card : board.getTODOS()
        ) {
            deleteCard(card);
        }
        deleteBoard(board);

    }

    public static void createBoard() {
        String postBoardPath = Board.BoardUrl_;
        Response response =
                given()
                        .spec(reqSpec)
                        .when()
                        .queryParam("name", board.getName())
                        .post(postBoardPath)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        String boardID = response.jsonPath().getString("id");
        board.setId_(boardID);
        System.out.println("Board oluşturuldu.");
    }

    public static void getLists() {
        String path = Board.BoardUrl_ + "/" + board.getId_() + "/lists";

        Response response =
                given()
                        .spec(reqSpec)
                        .when()
                        .get(path)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        String idValue = response.jsonPath().getString("[0].id");
        board.setTodoListID(idValue);
    }

    static void createCard(Card card) {
        getLists();
        String path = Card.cardUrl;
        Response response =
                given()
                        .spec(reqSpec)
                        .queryParam("idList", board.getTodoListID())
                        .queryParam("name", card.getCardName())
                        .when()
                        .post(path)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        String cardID = response.jsonPath().getString("id");
        card.setCardID(cardID);
        board.getTODOS().add(card);
        System.out.println(card.getCardName()+" isimli card oluşturuldu.");
    }


    static void updateCard(Card card) {
        String oldCardName=card.getCardName();
        String path = Card.cardUrl + "/" + card.getCardID();
        String newCardName = card.getCardName() + "updated";
        card.setCardName(newCardName);
        given()
                .spec(reqSpec)
                .queryParam("name", card.getCardName())
                .when()
                .put(path)
                .then()
                .statusCode(200)
                .extract()
        ;
        System.out.println(oldCardName+" isimli cardın ismi "+newCardName+" olarak değişti.");
    }

    static void deleteCard(Card card) {

        String path = Card.cardUrl + "/" + card.getCardID();
        given()
                .spec(reqSpec)
                .when()
                .delete(path)
                .then()
                .statusCode(200)
                .extract()
        ;
        System.out.println(card.getCardName()+" isimli card silindi.");
    }


    public static void deleteBoard(Board board) {
        String path = Board.BoardUrl_ + "/" + board.getId_();
        given()
                .spec(reqSpec)
                .when()
                .delete(path)
                .then()
                .statusCode(200)
                .extract()
        ;
        System.out.println("Board silindi.");
    }

    public static int getRandom(int size) {
        int randomChoice;
        randomChoice = (int) (Math.random() * size);
        return randomChoice;
    }
}
