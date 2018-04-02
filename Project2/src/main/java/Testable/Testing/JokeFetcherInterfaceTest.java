package Testable.Testing;

import Testable.DateFormatterInterfaceImpl;
import Testable.JokeException;
import Testable.JokeFetcher.FetcherFactoryImpl;
import Testable.JokeFetcher.JokeMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class JokeFetcherInterfaceTest {

    @Mock
    DateFormatterInterfaceImpl df;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }




    /*
    ALLL MY TESTS WENT OUT THE WINDOW AFTER REFRACTORING.... :S Double, Triple, Quadruple work. :D jk.


    @Test
    void getAvailableTypes() throws JokeException {
        JokeMaster jf = new JokeMaster(new DateFormatterInterfaceImpl());

        assertThat(jf.getAvailableTypes(), hasItems("eduprog","chucknorris","moma","tambal"));
    }

    @Test
    void getAvailableTypesFail() throws JokeException{
        JokeMaster jf = new JokeMaster(new DateFormatterInterfaceImpl());
        //assertThat(jf.getAvailableTypes(), hasItems("eduprog","chucknorris","moma","tambal", "ups"));
        assertThat(jf.getAvailableTypes(), not(hasItems("eduprog","chucknorris","moma","tambal", "ups")));
    }

    @DisplayName("Valid Strings")
    @ParameterizedTest
    @ValueSource(strings = {"eduprog","chucknorris","moma","tambal"})
    void isStringValidSucces(String teststring){
        JokeMaster jf = new JokeMaster(new DateFormatterInterfaceImpl());
        assertThat(jf.isStringValid(teststring), is(true));
    }

    @Test
    void isStringValidfail(){
        JokeMaster jf = new JokeMaster(new DateFormatterInterfaceImpl());
        assertThat(jf.isStringValid("SomeRandomString"), is(not(true)));
    }

    */



    @Test
    void BehaviorOfgetJokes() throws JokeException {
        JokeMaster jf = new JokeMaster(df, new FetcherFactoryImpl());
        given(df.getFormattedDate(ArgumentMatchers.eq("Europe/Copenhagen"), anyObject(), anyObject() )).willReturn("17 feb. 2018 10:56 AM");
        jf.getJokes("chucknorris", "Europe/Copenhagen");
        verify(df).getFormattedDate(ArgumentMatchers.anyString(), anyObject(), anyObject());


    }



}