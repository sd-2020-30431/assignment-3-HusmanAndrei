package ro.utcn.wasteless.mediator.handler;

import org.springframework.stereotype.Component;
import ro.utcn.wasteless.mediator.Handler;
import ro.utcn.wasteless.mediator.command.AddGrocery;
import ro.utcn.wasteless.mediator.response.AddGroceryResponse;
import ro.utcn.wasteless.service.WastelessServiceImpl;

@Component
public class AddGroceryHandler implements Handler<AddGrocery, AddGroceryResponse> {
    public AddGroceryHandler(WastelessServiceImpl wastelessService) {
        this.wastelessService = wastelessService;
    }

    @Override
    public AddGroceryResponse handle(AddGrocery addGrocery) {
        return new AddGroceryResponse(wastelessService.addGrocery(addGrocery.getGroceryItem(),addGrocery.getUser()));
    }
    private final WastelessServiceImpl wastelessService;
}
