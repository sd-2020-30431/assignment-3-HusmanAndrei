package ro.utcn.wasteless.observer.notifier;

import javafx.beans.InvalidationListener;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;
import ro.utcn.wasteless.domain.User;
import ro.utcn.wasteless.observer.DateChangeObservable;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
@Component
public class Notifyer implements Observer {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    private NotifyService notifyService;

    @Override
    public void update(Observable observable, Object o) {
       DateChangeObservable dateChangeObservable = (DateChangeObservable) observable;
       Date newDate = dateChangeObservable.getNewDate();
       try {
           System.out.println("HEHE" + newDate.toString());
           handleSendingNotifications(newDate);
       }
       catch (Exception ex){
           System.out.println("cannot send messaje");
       }
    }

    private void sendAll(){
        System.out.println("Sending to all ..");
        Greeting greeting = Greeting.builder().content("").type("general").build();
        simpMessageSendingOperations.convertAndSend("/topic/greetings", greeting);
    }

    private void sendUser(Long userId){
        System.out.println("Sending to " + userId);
        Greeting greeting =  new Greeting("You have surpassed your burndown rate for the next five days. Please try to manage your waste. We suggest donating items to your local food charities.", "individual");
        simpMessageSendingOperations.convertAndSend("/topic/greetings/" + userId, greeting);
    }

    private void handleSendingNotifications(Date currentDate){
        Map<User, Map<Date, Integer>> userResults = notifyService.getNonIdealUsersByBurndownRate(currentDate);
        List<Long> userIds = userResults.keySet().stream().map(usr -> usr.getId()).collect(Collectors.toList());
        userIds.forEach(this::sendUser);
        sendAll();
    }



}
