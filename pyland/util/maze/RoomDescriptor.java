package pyland.util.maze;

import pyland.model.IPlayer;
import pyland.model.IRoom;

class RoomDescriptor implements IDescriptor {

    // ATTRIBUTS STATIQUES

    private static final String PLAYER = "@";

    // ATTRIBUTS

    private IRoom room;

    // CONSTRUCTEURS

    RoomDescriptor(IRoom r) {
        room = r;
    }

    // REQUETES

    public String describe(boolean light) {
        IPlayer p = room.getVisitor();
        String res = "";
        if (p != null) {
            res += EMPTY1 + PLAYER + EMPTY1;
        } else {
            res += EMPTY3;
        }
        return res;
    }
}
