package ro.utcn.wasteless.mediator.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.wasteless.mediator.Request;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteItem implements Request {
    private Long itemId;
}