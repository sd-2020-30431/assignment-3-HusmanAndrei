package ro.utcn.wasteless.observer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("deprecation")
@Data
public class DateChangeObservable extends Observable {

    private Date newDate = null;


    public void changeDate(Date newDate){
        System.out.println("Observer change date " + newDate.toString());
        System.out.println("Obs: " + countObservers());
        this.newDate = newDate;

        this.setChanged();
        this.notifyObservers();
        this.notifyObservers(newDate);



    }



}
