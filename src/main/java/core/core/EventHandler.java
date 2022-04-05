package core.core;

import core.hotbar.HotbarEventHandler;

public class EventHandler {

    public void startEventHandlers(){
        startCoreEventHandler();
        startHotbarEventHandler();
    }

    private void startCoreEventHandler(){
        CoreEventHandler coreEventHandler = new CoreEventHandler();
        coreEventHandler.init();
    }

    private void startHotbarEventHandler(){
        HotbarEventHandler hotbarEventHandler = new HotbarEventHandler();
        hotbarEventHandler.init();
    }

}
