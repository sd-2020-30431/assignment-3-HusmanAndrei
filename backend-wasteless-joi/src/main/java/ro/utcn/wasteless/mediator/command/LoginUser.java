package ro.utcn.wasteless.mediator.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.wasteless.domain.GroceryItem;
import ro.utcn.wasteless.domain.User;
import ro.utcn.wasteless.mediator.Request;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements Request {
   private String user;
   private String password;


}
