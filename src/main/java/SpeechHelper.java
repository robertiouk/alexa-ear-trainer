import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.*;

/**
 * Contains commons speech helper methods.
 */
class SpeechHelper {
    private SpeechHelper() {}

    /**
     * Helper method for retrieving an Ask response with a simple card and reprompt included.
     * @param cardTitle Title of the card that you want displayed.
     * @param speechText speech text that will be spoken to the user.
     * @param isSsml determines whether the speech is SSML
     * @return the resulting card and speech text.
     */
    static SpeechletResponse getAskResponse(final String cardTitle, final String speechText,
                                            final boolean isSsml) {
        final SimpleCard card = getSimpleCard(cardTitle, speechText);
        final OutputSpeech speech = isSsml ? getSsmlTextOutputSpeech(speechText) :
                getPlainTextOutputSpeech(speechText);
        final Reprompt reprompt = getReprompt(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    static SpeechletResponse getTellResponse(final String cardTitle, final String speechText,
                                             final boolean isSsml) {
        final SimpleCard card = getSimpleCard(cardTitle, speechText);
        final OutputSpeech speech = isSsml ? getSsmlTextOutputSpeech(speechText) :
                getPlainTextOutputSpeech(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Helper method that creates a card object.
     * @param title title of the card
     * @param content body of the card
     * @return SimpleCard the display card to be sent along with the voice response.
     */
    static SimpleCard getSimpleCard(final String title, final String content) {
        final SimpleCard card = new SimpleCard();
        card.setTitle(title);
        card.setContent(content);

        return card;
    }

    /**
     * Helper method for retrieving an OutputSpeech object when given a string of TTS.
     * @param speechText the text that should be spoken out to the user.
     * @return an instance of SpeechOutput.
     */
    static PlainTextOutputSpeech getPlainTextOutputSpeech(final String speechText) {
        final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return speech;
    }

    /**
     * Helper method for retrieving an OutputSpeech object when given a string of SSML.
     * @param ssmlText the SSML text
     * @return an instance of SpeechOutput
     */
    static SsmlOutputSpeech getSsmlTextOutputSpeech(final String ssmlText) {
        final SsmlOutputSpeech speech = new SsmlOutputSpeech();
        speech.setSsml(ssmlText);

        return speech;
    }

    /**
     * Helper method that returns a reprompt object. This is used in Ask responses where you want
     * the user to be able to respond to your speech.
     * @param outputSpeech The OutputSpeech object that will be said once and repeated if necessary.
     * @return Reprompt instance.
     */
    private static Reprompt getReprompt(final OutputSpeech outputSpeech) {
        final Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(outputSpeech);

        return reprompt;
    }
}


