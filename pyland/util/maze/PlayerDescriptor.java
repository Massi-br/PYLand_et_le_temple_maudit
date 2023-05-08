package pyland.util.maze;

import pyland.model.IPlayer;

class PlayerDescriptor implements IDescriptor {

    // ATTRIBUTS STATIQUES

    private static final String LIVE_PLAYER = "@";
    private static final String DEAD_PLAYER = "#";
    private static final String SUPER_PLAYER = "$";

    // ATTRIBUTS
    
    private final IPlayer player;

    // CONSTRUCTEURS

    PlayerDescriptor(IPlayer p) {
        player = p;
    }

    // REQUETES

    public String describe(boolean light) {
        if (player.isDead()) {
            return DEAD_PLAYER;
        } else if (player.getPowerLevel() > 0) {
            return SUPER_PLAYER;
        } else {
            return LIVE_PLAYER;
        }
    }
}
