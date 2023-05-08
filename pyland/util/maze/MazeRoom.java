package pyland.util.maze;

import pyland.model.DoomRoom;
import pyland.model.ExitRoom;
import pyland.model.IPlayer;
import pyland.model.IRoom;
import pyland.model.MagicRoom;
import pyland.model.MonsterRoom;
import pyland.model.NormalRoom;

/**
 * Pièces pour la constitution des labyrinthes.
 */
final class MazeRoom {

    // ATTRIBUTS STATIQUES
    
    private static final int MIN = 5;
    private static final int MAX = 15;

    // ATTRIBUTS

    private final IRoom room;
    private IDescriptor desc;
    private boolean visited;
    private boolean connected;

    // CONSTRUCTEURS

    static MazeRoom createDoomRoom(Maze m, int i, int j) {
        MazeRoom r = new MazeRoom(new DoomRoom());
        r.setDescriptor(DescriptorFactory.createDoom(r, m, i, j));
        return r;
    }

    static MazeRoom createExitRoom(Maze m, int i, int j) {
        MazeRoom r = new MazeRoom(new ExitRoom());
        r.setDescriptor(DescriptorFactory.createExit(r, m, i, j));
        return r;
    }

    static MazeRoom createMagicRoom(Maze m, int i, int j) {
        MazeRoom r = new MazeRoom(new MagicRoom());
        r.setDescriptor(DescriptorFactory.createMagic(r, m, i, j));
        return r;
    }

    static MazeRoom createNormalRoom(Maze m, int i, int j) {
        MazeRoom r = new MazeRoom(new NormalRoom());
        r.setDescriptor(DescriptorFactory.createNormal(r, m, i, j));
        return r;
    }

    static MazeRoom createMonsterRoom(Maze m, int i, int j) {
        int level = alea(MIN, MAX);
        MazeRoom r = new MazeRoom(new MonsterRoom(level));
        r.setDescriptor(DescriptorFactory.createMonster(r, m, i, j));
        return r;
    }

    private MazeRoom(IRoom r) {
        room = r;
        visited = false;
        connected = false;
        desc = null;
    }

    // REQUETES

    IDescriptor getDesc() {
        return desc;
    }

    IDescriptor getPlayerDesc() {
        IPlayer p = room.getVisitor();
        return p == null ? null : new PlayerDescriptor(p);
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

    void setDescriptor(IDescriptor d) {
        desc = d;
    }

    void setVisited() {
        visited = true;
    }

    void reset() {
        connected = false;
        visited = false;
    }

    // OUTILS

    /**
     * Valeur aléatoire entre a et b.
     */
    private static int alea(int a, int b) {
        return a + (int) (Math.random() * (b - a + 1));
    }
}
