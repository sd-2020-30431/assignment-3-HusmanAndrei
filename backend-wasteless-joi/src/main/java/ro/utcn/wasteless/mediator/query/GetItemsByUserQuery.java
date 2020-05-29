package ro.utcn.wasteless.mediator.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.wasteless.mediator.Request;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetItemsByUserQuery implements Request {
    private long userId;
}
