package intervals;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class to be used by GUI in order to handle generation and submission of Intervals.
 * 
 * @author Robertio
 *
 */
public class IntervalController implements IntervalContainer, IntervalGenerator {
	public static final int MAX_VALUE_EXCLUSIVE = 88;
	public static final int MINIMUM_VALUE = 39;
	/** The list of intervalTypes available. */
	private List<IntervalType> intervalTypes = null;
	/** The current generated interval. */
	private Interval currentInterval = null;

	/**
	 * Randomly generates the next interval from the given list.
	 */
	@Override
	public Interval getNextInterval() {
		if (intervalTypes == null) {
			return null;
		} else {
			currentInterval = pickRandomInterval();
			return currentInterval;
		}
	}

	@Override
	public List<IntervalType> getIntervalTypes() {
		return intervalTypes;
	}

	@Override
	public void setIntervalTypes(List<IntervalType> intervalTypes) {
		this.intervalTypes = intervalTypes;
	}

	@Override
	public Interval getCurrentInterval() {
		if (currentInterval == null) {
			currentInterval = getNextInterval();
		}
		return currentInterval;
	}

	/**
	 * Select an interval at random from the list.
	 * 
	 * @return IntervalType
	 */
	private Interval pickRandomInterval() {
		final Random rand = new Random();
		final int randomIndex = rand.nextInt(intervalTypes.size());
		final IntervalType intervalType = intervalTypes.get(randomIndex);

		final int pitch = ThreadLocalRandom.current()
				.nextInt(MINIMUM_VALUE, MAX_VALUE_EXCLUSIVE - intervalType.getValue());

		return new IntervalImpl(intervalType, pitch);
	}
}
