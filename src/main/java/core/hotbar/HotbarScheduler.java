package core.hotbar;

import java.util.ArrayList;
import java.util.List;

public class HotbarScheduler {

    private final List<String> scheduledMessages = new ArrayList<String>();
    private boolean hasScheduledMessage = false;

    private final String defaultMessage;

    public HotbarScheduler(String defaultMessage){
        this.defaultMessage = defaultMessage;
    }

    public void scheduleMessage(String message){
        this.scheduledMessages.add(message);
        this.hasScheduledMessage = true;
    }

    private void runScheduler(){

    }

    private void killScheduler(boolean forceKill){

    }

}
