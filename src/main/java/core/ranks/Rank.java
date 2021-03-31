package core.ranks;


import java.util.HashMap;

public enum Rank {
    OWNER, DEV, ADMIN, PLUS, NORMAL, BANNED;

    public static HashMap<Rank, Integer> rankPriorities = new HashMap<Rank, Integer>() {{
        put(BANNED, 0);
        put(NORMAL, 1);
        put(PLUS, 2);
        put(ADMIN, 3);
        put(DEV, 4);
        put(OWNER, 4);
    }};
}
