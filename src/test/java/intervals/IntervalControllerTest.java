package intervals;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

public class IntervalControllerTest {
    @Test
    public void testGetCurrentInterval() {
        final IntervalController controller = new IntervalController();

        controller.setIntervalTypes(Arrays.asList(IntervalType.values()));
        final Interval interval = controller.getCurrentInterval();

        assertNotNull(interval);
    }
}
