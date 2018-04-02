package Testable.Testing;

import Testable.DateFormatterInterface;
import Testable.Joke;
import Testable.JokeException;
import Testable.JokeFetcher.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FetcherFactoryTest {

    private JokeMaster jm;

    @Mock
    DateFormatterInterface df;

    @Mock FetcherFactory factory;

    @Mock Moma momafetcher;

    @Mock ChuckNorris chuckfetcher;

    @Mock EduJoke edufetcher;

    @Mock Tambal tambalfetcher;

    @Mock Joke eduJoke;

    @Mock Joke momaJoke;

    @Mock Joke chuckJoke;

    @Mock Joke tambalJoke;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);


        List<JokeFetcherInterface> fetchers = Arrays.asList(edufetcher, chuckfetcher, momafetcher, tambalfetcher);
        when(factory.getJokeFetchers(anyString())).thenReturn(fetchers);
        List<String> types = Arrays.asList("eduprog","chucknorris","moma","tambal");
        when(factory.getAvailableTypes()).thenReturn(types);
        when(momafetcher.getJoke()).thenReturn(eduJoke);
        when(edufetcher.getJoke()).thenReturn(momaJoke);
        when(chuckfetcher.getJoke()).thenReturn(chuckJoke);
        when(tambalfetcher.getJoke()).thenReturn(tambalJoke);
        when(eduJoke.getJoke()).thenReturn("edu joke");
        when(momaJoke.getJoke()).thenReturn("moma joke");
        when(tambalJoke.getJoke()).thenReturn("tambal joke");
        when(chuckJoke.getJoke()).thenReturn("chuck joke");
        jm = new JokeMaster(df, factory);


    }

    @Test
    void getAvailableTypes() {

            FetcherFactory FF = new FetcherFactoryImpl();

            assertThat(FF.getAvailableTypes(), hasItems("eduprog","chucknorris","moma","tambal"));


    }

    @Test
    void getJokeFetchers() {

        FetcherFactory FF = new FetcherFactoryImpl();

        List<JokeFetcherInterface> result = FF.getJokeFetchers("eduprog,chucknorris,moma,tambal");
        assertThat(result, hasItems(instanceOf(EduJoke.class), instanceOf(ChuckNorris.class), instanceOf(Moma.class), instanceOf(Tambal.class)));



    }

    @Test
    void testGetJoke() throws JokeException {

        List<Joke> jokes = jm.getJokes("eduprog,chucknorris,moma,tambal", "Europe/Copenhagen").getJokes();

        assertThat(jokes, hasItems(eduJoke, momaJoke, tambalJoke, chuckJoke));
        assertThat(jokes.get(0).getJoke(), containsString("joke"));
        assertThat(jokes.get(1).getJoke(), containsString("joke"));
        assertThat(jokes.get(2).getJoke(), containsString("joke"));
        assertThat(jokes.get(3).getJoke(), containsString("joke"));
        verify(df).getFormattedDate(ArgumentMatchers.anyString(), anyObject(), anyObject());
        verify(factory, times(4)).getAvailableTypes();
        verify(factory).getJokeFetchers(anyString());
        verify(momafetcher).getJoke();
        verify(edufetcher).getJoke();
        verify(tambalfetcher).getJoke();
        verify(chuckJoke).getJoke();




    }

}