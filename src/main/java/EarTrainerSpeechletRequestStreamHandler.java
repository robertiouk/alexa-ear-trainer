/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * The handler for AWS Lambda function for the Ear Trainer application.
 */
public class EarTrainerSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds;

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<String>();
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
