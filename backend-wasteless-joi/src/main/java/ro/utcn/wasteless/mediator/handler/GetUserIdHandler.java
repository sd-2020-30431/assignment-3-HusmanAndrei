package ro.utcn.wasteless.mediator.handler;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcn.wasteless.mediator.Handler;
import ro.utcn.wasteless.mediator.query.GetUserId;
import ro.utcn.wasteless.mediator.response.GetUserIdResponse;
import ro.utcn.wasteless.service.WastelessService;

@Component
public class GetUserIdHandler implements Handler<GetUserId, GetUserIdResponse> {
    @Override
    public GetUserIdResponse handle(GetUserId getUserId) {
        return new GetUserIdResponse(wastelessService.getUserById(getUserId.getUserId()));
    }
    @Autowired
    private WastelessService wastelessService;
}
