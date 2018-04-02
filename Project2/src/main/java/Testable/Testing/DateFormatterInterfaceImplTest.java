package Testable.Testing;

import Testable.DateFormatterInterfaceImpl;
import Testable.JokeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.mockito.Mockito.verify;

class DateFormatterInterfaceImplTest {


    Date d = new Date(118, 2, 26, 2, 42, 0);

    @Spy
    SimpleDateFormat sdf = new SimpleDateFormat();




    String tz = "Europe/Copenhagen";

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getFormattedDate() throws JokeException {



        DateFormatterInterfaceImpl df = new DateFormatterInterfaceImpl();
        System.out.println(tz);
        System.out.println(d.toString());
        System.out.println(sdf.toString());
        //doNothing().when(sdf).setTimeZone(TimeZone.getTimeZone(tz));
        Assertions.assertEquals(df.getFormattedDate(tz, sdf, d), "26/03/2018 02.42");
        //verify(df).getFormattedDate(tz, sdf, d);
        verify(sdf).setTimeZone(TimeZone.getTimeZone(tz));
        //verify(sdf).format(d);
    }

}