package intervals;
import java.util.List;

/**
 * Holds a collection of Intervals.
 * 
 * @author Robertio
 *
 */
public interface IntervalContainer {
	List<IntervalType> getIntervalTypes();
	void setIntervalTypes(List<IntervalType> intervalTypes);
	Interval getCurrentInterval();
}
