package ro.utcn.wasteless.mediator.response;

import ro.utcn.wasteless.domain.GroceryItem;
import ro.utcn.wasteless.mediator.Response;

public class AddGroceryResponse implements Response {
    private GroceryItem groceryItem;

    public AddGroceryResponse(GroceryItem groceryItem) {
        this.groceryItem = groceryItem;
    }

    public GroceryItem getGroceryItem() {
        return groceryItem;
    }

    public void setGroceryItem(GroceryItem groceryItem) {
        this.groceryItem = groceryItem;
    }
}
