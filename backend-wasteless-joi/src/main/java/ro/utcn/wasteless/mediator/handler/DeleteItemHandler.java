package ro.utcn.wasteless.mediator.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcn.wasteless.mediator.Handler;
import ro.utcn.wasteless.mediator.command.DeleteItem;
import ro.utcn.wasteless.mediator.response.DeleteItemResponse;
import ro.utcn.wasteless.service.WastelessService;

@Component
public class DeleteItemHandler implements Handler<DeleteItem, DeleteItemResponse> {

    @Autowired
    private WastelessService wastelessService;

    @Override
    public DeleteItemResponse handle(DeleteItem deleteItem) {
        return new DeleteItemResponse(wastelessService.deleteItem(deleteItem.getItemId()));
    }
}
