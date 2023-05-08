package pyland.util.maze;

import pyland.model.IRoom;
import pyland.model.IRoomNetwork;
import pyland.model.RoomNetworkFactory;
import pyland.util.Direction;

/**
 * Bibliothèque de fonctions pour dessiner le labyrinthe.
 */
final class BorderFactory {

    // ATTRIBUTS STATIQUES

    private static final int VERTICAL = 0;
    private static final int HORIZONTAL = 1;
    private static final String CORNER = "+";
    private static final String FLOOR = "---";
    private static final String WALL = "|";
    private static final String CROSSING = "~~~";

    // METHODES STATIQUES

    static IDescriptor createCorner(MazeRoom ne, MazeRoom se,
            MazeRoom sw, MazeRoom nw) {
        return new AbstractDescriptor(new MazeRoom[] {ne, se, sw, nw}) {
            protected String fullVision() {
                return CORNER;
            }
            protected String noVision() {
                return EMPTY1;
            }
        };
    }

    static IDescriptor createFloor(MazeRoom n, MazeRoom s) {
        return new AbstractDescriptor(new MazeRoom[] { n, s }) {
            protected String fullVision() {
                return isWay(VERTICAL) ? EMPTY3 : FLOOR;
            }
            protected String noVision() {
                return EMPTY3;
            }
        };
    }

    static IDescriptor createWall(MazeRoom e, MazeRoom w) {
        return new AbstractDescriptor(new MazeRoom[] { e, w }) {
            protected String fullVision() {
                return isWay(HORIZONTAL) ? EMPTY1 : WALL;
            }
            protected String noVision() {
                return EMPTY1;
            }
        };
    }

    static IDescriptor createEmpty() {
        return new IDescriptor() {
            public String describe(boolean withLight) {
                return EMPTY3;
            }
        };
    }

    static IDescriptor createCrossing() {
        return new IDescriptor() {
            public String describe(boolean withLight) {
                return CROSSING;
            }
        };
    }

    // CONSTRUCTEURS

    private BorderFactory() {
        // pas d'instanciation possible
    }

    // TYPES IMBRIQUES

    private abstract static class AbstractDescriptor implements IDescriptor {

        private final MazeRoom[] rooms;

        private AbstractDescriptor(MazeRoom[] rs) {
            rooms = rs;
        }

        public String describe(boolean withLight) {
            if (isBorder() || someRoomVisited() || withLight) {
                return fullVision();
            } else {
                return noVision();
            }
        }

        protected abstract String fullVision();

        protected abstract String noVision();

        protected MazeRoom[] rooms() {
            return rooms;
        }

        protected boolean isBorder() {
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i] == null) {
                    return true;
                }
            }
            return false;
        }

        protected boolean someRoomVisited() {
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i].isVisited()) {
                    return true;
                }
            }
            return false;
        }

        protected boolean isWay(int position) {
            if (isBorder()) {
                return false;
            }
            IRoomNetwork net = RoomNetworkFactory.get();
            if (position == VERTICAL) {
                IRoom n = rooms()[0].getRoom();
                IRoom s = rooms()[1].getRoom();
                return net.canExit(n, Direction.SOUTH)
                        || net.canExit(s, Direction.NORTH);
            } else {
                IRoom e = rooms()[0].getRoom();
                IRoom w = rooms()[1].getRoom();
                return net.canExit(e, Direction.WEST)
                        || net.canExit(w, Direction.EAST);
            }
        }
    }
}
