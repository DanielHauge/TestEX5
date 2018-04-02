package Testable;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatterInterfaceImpl implements DateFormatterInterface {

    @Override
    public String getFormattedDate(String timeZone, SimpleDateFormat simpleFormat, Date date) throws JokeException {
        if(!Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZone)){
            throw new JokeException("Illegal Time Zone String");
        }
        simpleFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleFormat.format(date);
    }

}
