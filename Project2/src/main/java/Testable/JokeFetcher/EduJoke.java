package Testable.JokeFetcher;

import Testable.Joke;
import com.jayway.restassured.response.ExtractableResponse;

import static com.jayway.restassured.RestAssured.given;

public class EduJoke implements JokeFetcherInterface {
    @Override
    public Joke getJoke() {
        try{
            ExtractableResponse res =  given().get("http://jokes-plaul.rhcloud.com/api/joke").then().extract();
            String joke = res.path("joke");
            String reference = res.path("reference");
            return new Joke(joke,reference);
        }catch(Exception e){
            return null;
        }
    }
}
