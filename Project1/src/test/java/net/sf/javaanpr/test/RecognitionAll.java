package net.sf.javaanpr.test;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import org.junit.Rule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.rules.ErrorCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;


public class RecognitionAll {



    private static final Logger logger = LoggerFactory.getLogger(RecognitionAll.class);

    @Rule
    public ErrorCollector recognitionErrors = new ErrorCollector();



    @ParameterizedTest(name = "{0}")
    @CsvFileSource(resources = "/results.csv")
    void TestSnapShots(String image, String expected) throws Exception {
        logger.info(image+ " Should be "+expected+"\n\n");

        CarSnapshot carSnap = new CarSnapshot("snapshots/"+image);
        assertThat(carSnap, is(not(nullValue())));
        assertThat(carSnap.getImage(), is(not(nullValue())));
        Intelligence intel = new Intelligence();
        assertThat(intel, is(not(nullValue())));
        String result = intel.recognize(carSnap);
        assertThat(result, is(not(nullValue())));
        assertThat(result, equalTo(expected));
        carSnap.close();
    }


}
