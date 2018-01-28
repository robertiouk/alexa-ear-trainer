package intervals;

/**
 * Defines an interval, with the start and end pitches.
 */
public interface Interval {
    IntervalType getIntervalType();
    int getStartPitch();
    int getEndPitch();
}
