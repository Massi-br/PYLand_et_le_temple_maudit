package pyland.util.maze;

import pyland.model.IRoom;

/**
 * Pièces pour la constitution des labyrinthes.
 */
class MazeRoom {

    // ATTRIBUTS

    private final IRoom room;
    private final IDescriptor desc;
    private boolean visited;
    private boolean connected;

    // CONSTRUCTEURS

    MazeRoom(IRoom r) {
        room = r;
        visited = false;
        connected = false;
        desc = new RoomDescriptor(r);
    }

    // REQUETES

    IDescriptor getDesc() {
        return desc;
    }

    IRoom getRoom() {
        return room;
    }

    boolean isConnected() {
        return connected;
    }

    boolean isVisited() {
        return visited;
    }

    // COMMANDES

    void setConnected() {
        connected = true;
    }

    void setVisited() {
        visited = true;
    }

    void reset() {
        connected = false;
        visited = false;
    }
}
