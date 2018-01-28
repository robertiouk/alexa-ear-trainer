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
	Minor2nd(1, Collections.singletonList("Minor Second")),
	Major2nd(2, Collections.singletonList("Major Second")),
	Minor3rd(3, Collections.singletonList("Minor Third")),
	Major3rd(4, Collections.singletonList("Major Third")),
	Perfect4th(5, Collections.singletonList("Perfect Fourth")),
	Diminished5th(6, Arrays.asList("Diminished Fifth", "Tritone")),
	Perfect5th(7, Collections.singletonList("Perfect Fifth")),
	Minor6th(8, Collections.singletonList("Minor Sixth")),
	Major6th(9, Collections.singletonList("Major Sixth")),
	Minor7th(10, Collections.singletonList("Minor Seventh")),
	Major7th(11, Collections.singletonList("Major Seventh")),
	Octave(12, Arrays.asList("Perfect Octave", "Octave")),
	Minor9th(13, Collections.singletonList("Minor Ninth")),
	Major9th(14, Collections.singletonList("Major Ninth")),
	Augmented9th(15, Collections.singletonList("Augmented Ninth")),
	Major10th(16, Collections.singletonList("Major Tenth")),
	Perfect11th(17, Collections.singletonList("Perfect Eleventh")),
	Major13th(21, Collections.singletonList("Major Thirteenth"));

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
				.filter(interval -> interval.aliases.contains(alias))
				.findAny()
				.orElseThrow(() ->
						new IllegalArgumentException(String.format("%s not recognised", alias)));
	}
}
