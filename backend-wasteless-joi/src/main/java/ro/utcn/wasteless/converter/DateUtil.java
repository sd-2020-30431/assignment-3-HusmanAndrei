package ro.utcn.wasteless.converter;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {
    public static Date convertStringToDate(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        try {

            Date date = formatter.parse(dateString);
            System.out.println(date);
            System.out.println(formatter.format(date));
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(date);
    }
}
