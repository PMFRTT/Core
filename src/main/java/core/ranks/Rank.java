package core.ranks;


import java.util.HashMap;

public enum Rank {
    OWNER, DEV, ADMIN, PLUS, NORMAL, BANNED;

    public static HashMap<Integer, Rank> rankPriorities = new HashMap<Integer, Rank>() {{
        put(0, BANNED);
        put(1, NORMAL);
        put(2, PLUS);
        put(3, ADMIN);
        put(4, DEV);
        put(5, OWNER);
    }};

    public static Rank convertIntToRank(int i) {
        return rankPriorities.get(i);
    }
}