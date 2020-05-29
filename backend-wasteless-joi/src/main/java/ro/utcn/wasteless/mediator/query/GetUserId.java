package ro.utcn.wasteless.mediator.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ro.utcn.wasteless.mediator.Request;
import ro.utcn.wasteless.service.WastelessService;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetUserId implements Request {
    private long userId;

}
