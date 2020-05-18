package ro.utcn.wasteless.observer.notifier;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.Map;

@Controller
public class NotifyController {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/hello")
    public void greeting(HelloMessage message, @Header("simpSessionId") String sessionId) throws Exception {
        Thread.sleep(1000); // simulated delay
        Greeting greeting =  new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!", "general");

        simpMessageSendingOperations.convertAndSend("/topic/greetings/" + message.getUserId(), greeting);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}