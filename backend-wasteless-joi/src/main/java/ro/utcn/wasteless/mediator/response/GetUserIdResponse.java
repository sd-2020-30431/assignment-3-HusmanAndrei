package ro.utcn.wasteless.mediator.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.wasteless.domain.User;
import ro.utcn.wasteless.mediator.Response;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserIdResponse implements Response {
    private Optional<User> userOptional;
}
