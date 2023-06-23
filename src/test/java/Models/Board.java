package Models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public Board(String name) {
        setName(name);
    }

   private String name;

   private String id_;

   public static final String BoardUrl_="boards";

   private String todoListID;

   private final List<Card>  TODOS=new ArrayList<>();

    public List<Card> getTODOS() {
        return TODOS;
    }



    public String getTodoListID() {
        return todoListID;
    }

    public void setTodoListID(String todoListID) {
        this.todoListID = todoListID;
    }



    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
