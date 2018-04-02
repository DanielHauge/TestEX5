package Testable;


import Testable.JokeFetcher.FetcherFactoryImpl;
import Testable.JokeFetcher.JokeMaster;

public class Main {


    public static void main(String[] args) throws JokeException {

        JokeMaster JF = new JokeMaster(new DateFormatterInterfaceImpl(), new FetcherFactoryImpl());
        Jokes jokes = JF.getJokes("chucknorris", "Europe/Copenhagen");
        jokes.getJokes().forEach((joke) -> {
            System.out.println(joke);
        });


        System.out.println("Is String Valid: "+JF.isStringValid("edu_prog,xxx"));
    }
}
