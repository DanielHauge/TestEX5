package Testable;


import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateFormatterInterface {

    public String getFormattedDate(String timeZone, SimpleDateFormat simpleFormat, Date date) throws JokeException;

}
