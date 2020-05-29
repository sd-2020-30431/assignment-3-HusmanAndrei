package ro.utcn.wasteless.mediator.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.wasteless.domain.GroceryItem;
import ro.utcn.wasteless.mediator.Response;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetItemsByUserResponse implements Response {
    private List<GroceryItem> groceryItems;

}
