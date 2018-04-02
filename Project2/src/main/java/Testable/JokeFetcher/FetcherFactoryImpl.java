package Testable.JokeFetcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FetcherFactoryImpl implements FetcherFactory {

    private final List<String> availableTypes = Arrays.asList("eduprog","chucknorris","moma","tambal");

    @Override
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public List<JokeFetcherInterface> getJokeFetchers(String jokesToFetch) {

        List<JokeFetcherInterface> result = new ArrayList<JokeFetcherInterface>();
        String[] tokens = jokesToFetch.split(",");
        for(String token : tokens){
            switch(token){
                case "eduprog" : result.add(new EduJoke());break;
                case "chucknorris" : result.add(new ChuckNorris());break;
                case "moma" : result.add(new Moma());break;
                case "tambal" : result.add(new Tambal());break;
            }
        }

        return result;
    }

}
