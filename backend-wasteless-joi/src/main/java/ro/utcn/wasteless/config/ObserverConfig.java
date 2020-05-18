package ro.utcn.wasteless.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ro.utcn.wasteless.observer.DateChangeObservable;

import java.util.Observer;

@Configuration
@SuppressWarnings("deprecation")
public class ObserverConfig {

//    @Autowired
//    NotifyService notifyService;
//
//    @Autowired
//    private SimpMessageSendingOperations simpMessageSendingOperations;
//
//
//    @Bean
//    public Observer getNotifier() {
//        return new Notifyer(simpMessageSendingOperations, notifyService);
//    }
    @Autowired
    private Observer notifyer;

    @Bean
    public DateChangeObservable getDateChangeObservable(){

        System.out.println("Creating observer");
        DateChangeObservable dateChangeObservable = new DateChangeObservable();
        dateChangeObservable.addObserver(notifyer);
        System.out.println("Nr of observers : " + dateChangeObservable.countObservers());

        return dateChangeObservable;
    }
}
