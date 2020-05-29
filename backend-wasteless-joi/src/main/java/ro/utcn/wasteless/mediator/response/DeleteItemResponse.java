package ro.utcn.wasteless.mediator.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ro.utcn.wasteless.domain.GroceryItem;
import ro.utcn.wasteless.mediator.Response;

import java.util.Optional;

@SuppressWarnings("ALL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteItemResponse implements Response {
    private Optional<GroceryItem> groceryItem;
}
