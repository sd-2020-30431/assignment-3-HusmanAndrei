package ro.utcn.wasteless.mediator.command;

import ro.utcn.wasteless.domain.GroceryItem;
import ro.utcn.wasteless.domain.User;
import ro.utcn.wasteless.mediator.Request;

public class AddGrocery implements Request {
    private GroceryItem groceryItem;
    private User user;

    public AddGrocery(GroceryItem groceryItem, User user) {
        this.groceryItem = groceryItem;
        this.user = user;
    }

    public GroceryItem getGroceryItem() {
        return groceryItem;
    }

    public void setGroceryItem(GroceryItem groceryItem) {
        this.groceryItem = groceryItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

