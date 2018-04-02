package Testable.JokeFetcher;

import Testable.DateFormatterInterface;
import Testable.JokeException;
import Testable.Jokes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JokeMaster {

    private DateFormatterInterface df;
    private FetcherFactory factory;



    public JokeMaster(DateFormatterInterface DF, FetcherFactory factory) {
        this.df = DF;
        this.factory = factory;
    }




    public boolean isStringValid(String jokeTokens) {
        String[] tokens = jokeTokens.split(",");
        for(String token: tokens){
            if(!factory.getAvailableTypes().contains(token)){
                return false;
            }
        }
        return true;
    }


    public Jokes getJokes(String jokesToFetch, String timeZone) throws JokeException {
        if(!isStringValid(jokesToFetch)){
            throw new JokeException("Inputs (jokesToFetch) contain types not recognized");
        }
        Jokes jokes = new Jokes();
        for(JokeFetcherInterface fetcher : factory.getJokeFetchers(jokesToFetch)){
            jokes.addJoke(fetcher.getJoke());
        }
        String timeZoneString = df.getFormattedDate(timeZone, new SimpleDateFormat(), new Date());
        jokes.setTimeZoneString(timeZoneString);
        return jokes;
    }
}
