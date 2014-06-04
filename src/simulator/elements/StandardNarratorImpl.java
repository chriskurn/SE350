package simulator.elements;

import java.util.concurrent.TimeUnit;

/**
 * Description: StandardNarratorImpl class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class StandardNarratorImpl implements Narrator {

    /** The narration creation time. */
    private final long narrationCreationTime;

    /**
     * Instantiates a new standard narrator impl.
     * 
     */
    public StandardNarratorImpl() {
        narrationCreationTime = System.currentTimeMillis();
    }

    /**
     * Method that logs elevator events.
     * 
     * @see simulator.elements.Narrator#logEvent(java.lang.String)
     */
    @Override
    public void logEvent(String event) {

        String logMessage = getCurrentElapsedTime() + " " + event;
        System.out.println(logMessage);
    }

    /**
     * Utility method for determining the delta time since the narrator has
     * begun.
     * 
     * @return a string representing the delta time. The format is:
     *         hh:min:seconds.miliseconds
     */
    private String getCurrentElapsedTime() {

        long l = System.currentTimeMillis() - getNarrationCreationTime();

        long hr = TimeUnit.MILLISECONDS.toHours(l);
        long min = TimeUnit.MILLISECONDS.toMinutes(l
                - TimeUnit.HOURS.toMillis(hr));

        long sec = TimeUnit.MILLISECONDS.toSeconds(l
                - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));

        long ms = TimeUnit.MILLISECONDS.toMillis(l
                - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min)
                - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);

    }

    /**
     * Private get method for returning the creation time member.
     * 
     * @return returns the creation time of this class
     */
    private long getNarrationCreationTime() {
        return narrationCreationTime;
    }

}
