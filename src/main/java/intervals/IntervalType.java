package intervals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Enumeration to contain the interval type
 * 
 * @author Robertio
 *
 */
public enum IntervalType {
	Unison(0, Collections.singletonList("Unison")),
	Minor2nd(1, Arrays.asList("Minor Second", "Minor 2nd")),
	Major2nd(2, Arrays.asList("Major Second", "Major 2nd")),
	Minor3rd(3, Arrays.asList("Minor Third", "Minor 3rd")),
	Major3rd(4, Arrays.asList("Major Third", "Major 3rd")),
	Perfect4th(5, Arrays.asList("Perfect Fourth", "Perfect 4th")),
	Diminished5th(6, Arrays.asList("Diminished Fifth", "Tritone", "Diminished 5th")),
	Perfect5th(7, Arrays.asList("Perfect Fifth", "Perfect 5th")),
	Minor6th(8, Arrays.asList("Minor Sixth", "Minor 6th")),
	Major6th(9, Arrays.asList("Major Sixth", "Major 6th")),
	Minor7th(10, Arrays.asList("Minor Seventh", "Minor 7th")),
	Major7th(11, Arrays.asList("Major Seventh", "Major 7th")),
	Octave(12, Arrays.asList("Perfect Octave", "Octave")),
	Minor9th(13, Arrays.asList("Minor Ninth", "Minor 9th")),
	Major9th(14, Arrays.asList("Major Ninth", "Major 9th")),
	Augmented9th(15, Arrays.asList("Augmented Ninth", "Augmented 9th")),
	Major10th(16, Arrays.asList("Major Tenth", "Major 10th")),
	Perfect11th(17, Arrays.asList("Perfect Eleventh", "Perfect 11th")),
	Major13th(21, Arrays.asList("Major Thirteenth", "Major 13th"));

	private int value;
	private List<String> aliases;

    IntervalType(final int value, final List<String> aliases) {
    	this.value = value;
        this.aliases = aliases;
    }
    
    public int getValue() {
    	return value;
    }

    /**
     * Get the preferred alias for an interval.
     * @return the preferred alias
     */
    public String getAlias() {
        return aliases.stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Interval has no alias'"));
    }

    /**
     * Find an Interval from a given alias.
     * @param alias the interval alias
     * @return the Interval
     */
    public static IntervalType fromAlias(final String alias) {
    	return Stream.of(IntervalType.values())
				.filter(interval -> interval.aliases.stream()
                        .map(String::toLowerCase)
                        .anyMatch(name -> alias.toLowerCase().equals(name)))
				.findAny()
				.orElseThrow(() ->
						new IllegalArgumentException(String.format("%s not recognised", alias)));
	}
}
