import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.SsmlOutputSpeech;
import intervals.Interval;
import intervals.IntervalController;
import intervals.IntervalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

/**
 * Handles speech requests for the ear trainer.
 */
public class EarTrainerSpeechlet implements SpeechletV2 {

    private static final Logger LOG = LoggerFactory.getLogger(EarTrainerSpeechlet.class);
    private static final String CARD_TITLE = "Ear Trainer";
    private static final String UNKNOWN_INTENT = "I'm sorry, I did not understand the response";
    private static final String NOTE_SSML = "<audio src=\"https://s3.amazonaws.com/robertio-intervals/%s\" />";
    private static final String INTERVAL_SLOT = "IntervalName";

    private final IntervalController intervalController = new IntervalController();

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        LOG.info("onSessionStarted requd={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());

        intervalController.setIntervalTypes(Arrays.asList(IntervalType.values()));
    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        Session session = requestEnvelope.getSession();
        LOG.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                session.getSessionId());

        return askInterval(intervalController.getNextInterval(), Optional.empty());
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        final IntentRequest request = requestEnvelope.getRequest();
        LOG.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());

        final Intent intent = request.getIntent();
        final String intentName = (intent != null) ? intent.getName() : null;

        if ("IntervalIntent".equals(intentName)) {
            return askInterval(intervalController.getNextInterval(), Optional.empty());
        } else if ("AnswerIntent".equals(intentName)) {
            return validateAnswer(intent);
        } else if ("RepeatIntent".equals(intentName)) {
            return askInterval(intervalController.getCurrentInterval(), Optional.empty());
        } else if ("SkipIntent".equals(intentName)) {
            final String currentInterval = intervalController.getCurrentInterval().getIntervalType().getAlias();
            return askInterval(intervalController.getNextInterval(), Optional.of(
                    String.format("Ok. The correct answer was %s.", currentInterval)));
        } else if ("DebugIntent".equals(intentName)) {
            LOG.info(getIntervalSsml(intervalController.getCurrentInterval()));
            return askInterval(intervalController.getNextInterval(), Optional.empty());
        } else if ("AMAZON.RepeatIntent".equals(intentName)) {
            return askInterval(intervalController.getCurrentInterval(), Optional.empty());
        } else if ("AMAZON.StopIntent".equals(intentName)) {
            return SpeechHelper.getTellResponse(CARD_TITLE, "Goodbye", false);
        } else if ("AMAZON.CancelIntent".equals(intentName)) {
            return SpeechHelper.getTellResponse(CARD_TITLE, "Goodbye", false);
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else {
            return getUnknownResponse();
        }

    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        LOG.info("onSessionEnded requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());

        // any session cleanup logic would go here
    }

    private SpeechletResponse askInterval(final Interval interval, final Optional<String> pretext) {
        final String ssmlInterval =getIntervalSsml(interval);

        final String response = pretext.map(text -> "<speak>\n" + text +
                "\n" + ssmlInterval +
                "\n</speak>")
                .orElse("<speak>\n" + ssmlInterval + "\n</speak>");
        LOG.info("Responding with " + response);

        return SpeechHelper.getAskResponse(CARD_TITLE, response, true);
    }

    private String getIntervalSsml(final Interval interval) {
        final String startNote = String.format("%d.mid.mp3", interval.getStartPitch());
        final String endNote = String.format("%d.mid.mp3", interval.getEndPitch());
        return String.format(NOTE_SSML, startNote) +
                String.format(NOTE_SSML, endNote);
    }

    private SpeechletResponse validateAnswer(final Intent intent) {
        final Slot slot = intent.getSlot(INTERVAL_SLOT);

        final String givenInterval = slot.getValue();
        try {
            final IntervalType intervalType = IntervalType.fromAlias(givenInterval);
            final IntervalType currentInterval = intervalController.getCurrentInterval().getIntervalType();
            final String response;
            if (intervalType.equals(currentInterval)) {
                response = "Correct";
            } else {
                response = String.format("Sorry, the correct answer is %s", currentInterval.getAlias());
            }

            return askInterval(intervalController.getNextInterval(), Optional.of(response));
            //return SpeechHelper.getTellResponse(CARD_TITLE, response, false);
        } catch (final IllegalArgumentException exception) {
            LOG.info("Couldn't get a match for " + givenInterval);
            /*return SpeechHelper.getTellResponse(CARD_TITLE,
                    String.format("I'm sorry, I don't recognise %s as in interval", givenInterval),
                    false);*/
            return askInterval(intervalController.getCurrentInterval(), Optional.of(
                    String.format("I'm sorry, I don't recognise %s as in interval", givenInterval)));
        }
    }

    private SpeechletResponse getUnknownResponse() {
        return SpeechHelper.getAskResponse(CARD_TITLE, UNKNOWN_INTENT, false);
    }

    private SpeechletResponse getHelpResponse() {
        final String speechText = "This is a help response placeholder";
        return SpeechHelper.getAskResponse(CARD_TITLE, speechText, false);
    }
}
