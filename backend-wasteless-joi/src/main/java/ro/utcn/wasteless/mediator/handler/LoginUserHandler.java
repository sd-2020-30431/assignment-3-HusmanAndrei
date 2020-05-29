package ro.utcn.wasteless.mediator.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcn.wasteless.mediator.Handler;
import ro.utcn.wasteless.mediator.command.AddGrocery;
import ro.utcn.wasteless.mediator.command.LoginUser;
import ro.utcn.wasteless.mediator.response.AddGroceryResponse;
import ro.utcn.wasteless.mediator.response.LoginUserResponse;
import ro.utcn.wasteless.service.WastelessService;
import ro.utcn.wasteless.service.WastelessServiceImpl;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserHandler implements Handler<LoginUser, LoginUserResponse> {


    @Override
    public LoginUserResponse handle(LoginUser loginUser) {
        return new LoginUserResponse(wastelessService.getUser(loginUser.getUser(), loginUser.getPassword()));
    }
    @Autowired
    private WastelessService wastelessService;
}
