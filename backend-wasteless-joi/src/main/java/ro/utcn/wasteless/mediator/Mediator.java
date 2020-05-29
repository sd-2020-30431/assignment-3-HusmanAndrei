package ro.utcn.wasteless.mediator;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import ro.utcn.wasteless.mediator.command.AddGrocery;
import ro.utcn.wasteless.mediator.command.DeleteItem;
import ro.utcn.wasteless.mediator.command.LoginUser;
import ro.utcn.wasteless.mediator.handler.*;
import ro.utcn.wasteless.mediator.query.GetItemsByUserQuery;
import ro.utcn.wasteless.mediator.query.GetUserId;
import ro.utcn.wasteless.mediator.response.GetItemsByUserResponse;

import java.util.HashMap;
import java.util.Map;

@Component
public class Mediator implements ApplicationContextAware {

    public Mediator() {
        plm = new HashMap<>();
        plm.put(AddGrocery.class, AddGroceryHandler.class);
        plm.put(LoginUser.class, LoginUserHandler.class);
        plm.put(GetItemsByUserQuery.class, GetItemsByUserHandler.class);
        plm.put(DeleteItem.class, DeleteItemHandler.class);
        plm.put(GetUserId.class, GetUserIdHandler.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
    private ApplicationContext applicationContext;
    private final Map<Class<? extends Request>, Class<? extends Handler<? extends Request, ? extends Response>>> plm;


    public <T extends Request, R extends Response> Handler<T, R> getHandler(T request) {
        Class<? extends Handler<? extends Request, ? extends Response>> hType = plm.get(request.getClass());
        return (Handler<T, R>) applicationContext.getBean(hType);
    }

}
