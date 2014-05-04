package simulator.elements;
/**
 * 
 * @author Patrick Stein, Chris Kurns
 *
 */
public class Narrator {
    
    private final boolean saveToFile;
    private String[] recentMessages;

    public Narrator(boolean stf, int sizeOfMessageQueue){
        //Do you want to save the log to a file
        saveToFile = stf;
        recentMessages = new String[sizeOfMessageQueue];
    }
    
    public void logEvent(String event){
        System.out.println(event);
    }
}
