package ro.utcn.wasteless.mediator.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcn.wasteless.domain.User;
import ro.utcn.wasteless.mediator.Handler;
import ro.utcn.wasteless.mediator.query.GetItemsByUserQuery;
import ro.utcn.wasteless.mediator.response.GetItemsByUserResponse;
import ro.utcn.wasteless.service.WastelessService;

import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Component
public class GetItemsByUserHandler implements Handler<GetItemsByUserQuery, GetItemsByUserResponse> {
    @Autowired
    private WastelessService wastelessService;

    @Override
    public GetItemsByUserResponse handle(GetItemsByUserQuery getItemsByUserQuery) {
        Optional<User> user = getUser(getItemsByUserQuery.getUserId());
        return new GetItemsByUserResponse(wastelessService.getItemsByUser(user.get()));
    }

    private Optional<User> getUser(Long id){
        return wastelessService.getUserById(id);
    }
}
