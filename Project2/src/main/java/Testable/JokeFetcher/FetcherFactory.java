package Testable.JokeFetcher;

import java.util.List;

public interface FetcherFactory {

    List<String> getAvailableTypes();
    List<JokeFetcherInterface> getJokeFetchers(String jokesToFetch);
}
