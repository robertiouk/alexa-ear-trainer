import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.speechlet.lambda.LambdaSpeechletRequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * A RequestStreamHandler implementation that won't throw exceptions when handed a
 * null speechlet request (generated from a scheduled CloudWatch trigger).
 */
public class PingableRequestStreamHandler implements RequestStreamHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PingableRequestStreamHandler.class);
    private final SpeechletV2 speechlet;
    private final SpeechletRequestHandler speechletRequestHandler;

    /** {@inheritDoc} */
    public PingableRequestStreamHandler(SpeechletV2 speechlet, Set<String> supportedApplicationIds) {
        this.speechlet = speechlet;
        this.speechletRequestHandler = new LambdaSpeechletRequestHandler(supportedApplicationIds);
    }

    /** {@inheritDoc} */
    public PingableRequestStreamHandler(Speechlet speechlet, Set<String> supportedApplicationIds) {
        this(new SpeechletToSpeechletV2Adapter(speechlet), supportedApplicationIds);
    }

    /** {@inheritDoc} */
    @Override
    public final void handleRequest(InputStream input, OutputStream output, Context context)
            throws IOException {
        byte[] serializedSpeechletRequest = IOUtils.toByteArray(input);

        if (isApplicationPing(serializedSpeechletRequest)) {
            // Don't do anything, this is just keeping the JVM warm
            LOG.debug("pong!");
        } else {
            byte[] outputBytes;
            try {
                outputBytes =
                        speechletRequestHandler.handleSpeechletCall(speechlet,
                                serializedSpeechletRequest);
            } catch (SpeechletRequestHandlerException | SpeechletException ex) {
                throw new RuntimeException(ex);
            }

            output.write(outputBytes);
        }
    }

    private boolean isApplicationPing(final byte[] serializedSpeechletRequest) throws IOException {
        final SpeechletRequestEnvelope<?> requestEnvelope =
                SpeechletRequestEnvelope.fromJson(serializedSpeechletRequest);

        return requestEnvelope.getRequest() == null;
    }
}
