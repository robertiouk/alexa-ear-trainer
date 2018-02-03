import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.amazonaws.services.lambda.runtime.Context;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * The handler for AWS Lambda function for the Ear Trainer application.
 */
public class EarTrainerSpeechletRequestStreamHandler extends PingableRequestStreamHandler {

    private static final Set<String> supportedApplicationIds;

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<>();
        supportedApplicationIds.add("amzn1.ask.skill.fcc3a5f1-c5bf-4dc1-a785-53cb6d95489e");
    }

    /**
     * Default constructor.
     */
    public EarTrainerSpeechletRequestStreamHandler() {
        super(new EarTrainerSpeechlet(), supportedApplicationIds);
    }

    /**
     * Constructor.
     * @param speechlet the speechlet implementation
     * @param supportedApplicationIds the supported application ID's
     */
    public EarTrainerSpeechletRequestStreamHandler(final Speechlet speechlet,
                                                   final Set<String> supportedApplicationIds) {
        super(speechlet, supportedApplicationIds);
    }
}
