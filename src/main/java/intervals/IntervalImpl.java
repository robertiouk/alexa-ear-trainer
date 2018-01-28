package intervals;

public class IntervalImpl implements Interval {
    private final IntervalType intervalType;
    private final int startPitch;

    public IntervalImpl(final IntervalType intervalType, final int startPitch) {
        this.intervalType = intervalType;
        this.startPitch = startPitch;
    }

    @Override
    public IntervalType getIntervalType() {
        return intervalType;
    }

    @Override
    public int getStartPitch() {
        return startPitch;
    }

    @Override
    public int getEndPitch() {
        return startPitch + intervalType.getValue();
    }
}
