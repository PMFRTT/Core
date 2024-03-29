package core.commands;


import core.core.CoreHandler;
import core.core.CoreMain;

import java.util.Objects;

public class MainCommandListener {

    private final CoreMain main = CoreHandler.getMain();

    private GameModeCommandListener gameModeCommandListener;
    private WeatherCommandListener weatherCommandListener;
    private TimeCommandListener timeCommandListener;
    private HubCommandListener hubCommandListener;
    private TeleportCommandListener teleportCommandListener;
    private HealCommandListener healCommandListener;
    private PingCommandListener pingCommandListener;
    private InvseeCommandListener invseeCommandListener;
    private DifficultyCommandListener difficultyCommandListener;
    private VersionCommandListener versionCommandListener;
    private PermissionCommandListener PermissionCommandListener;
    private SQLCommandListener sqlCommandListener;
    private BugCommandExecutor bugCommandExecutor;
    private ScheduleCommandListener scheduleCommandListener;

    public void init(){
        initListeners();
        prepareListeners();
    }

    private void initListeners(){
        gameModeCommandListener = new GameModeCommandListener();
        weatherCommandListener = new WeatherCommandListener();
        timeCommandListener = new TimeCommandListener();
        hubCommandListener = new HubCommandListener();
        teleportCommandListener = new TeleportCommandListener();
        healCommandListener = new HealCommandListener();
        pingCommandListener = new PingCommandListener();
        invseeCommandListener = new InvseeCommandListener();
        difficultyCommandListener = new DifficultyCommandListener();
        versionCommandListener = new VersionCommandListener(this.main);
        PermissionCommandListener = new PermissionCommandListener(this.main);
        sqlCommandListener = new SQLCommandListener();
        bugCommandExecutor = new BugCommandExecutor();
        scheduleCommandListener = new ScheduleCommandListener();

    }


    private void prepareListeners() {

        Objects.requireNonNull(this.main.getCommand("Gamemode")).setExecutor(gameModeCommandListener);
        Objects.requireNonNull(this.main.getCommand("Weather")).setExecutor(weatherCommandListener);
        Objects.requireNonNull(this.main.getCommand("Time")).setExecutor(timeCommandListener);
        Objects.requireNonNull(this.main.getCommand("Core")).setExecutor(versionCommandListener);
        Objects.requireNonNull(this.main.getCommand("Allow")).setExecutor(PermissionCommandListener);
        Objects.requireNonNull(this.main.getCommand("Revoke")).setExecutor(PermissionCommandListener);
        Objects.requireNonNull(this.main.getCommand("hub")).setExecutor(hubCommandListener);
        Objects.requireNonNull(this.main.getCommand("Heal")).setExecutor(healCommandListener);
        Objects.requireNonNull(this.main.getCommand("Difficulty")).setExecutor(difficultyCommandListener);
        Objects.requireNonNull(this.main.getCommand("Permissions")).setExecutor(PermissionCommandListener);
        Objects.requireNonNull(this.main.getCommand("ping")).setExecutor(pingCommandListener);
        Objects.requireNonNull(this.main.getCommand("invsee")).setExecutor(invseeCommandListener);
        Objects.requireNonNull(this.main.getCommand("teleport")).setExecutor(teleportCommandListener);
        Objects.requireNonNull(this.main.getCommand("sql")).setExecutor(sqlCommandListener);
        Objects.requireNonNull(this.main.getCommand("bug")).setExecutor(bugCommandExecutor);
        Objects.requireNonNull(this.main.getCommand("schedule")).setExecutor(scheduleCommandListener);
    }

}
