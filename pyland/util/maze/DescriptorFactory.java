package pyland.util.maze;

import pyland.model.IMonsterRoom;
import pyland.model.IRoom;

final class DescriptorFactory {

    // ATTRIBUTS STATIQUES

    private static final String DOOM = "x";
    private static final String MAGIC = "!";
    private static final String EXIT = "S";

    // CONSTRUCTEURS

    private DescriptorFactory() {
        // rien
    }

    // METHODES STATIQUES

    static IDescriptor createDoom(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            protected String deepVision() {
                return EMPTY1 + DOOM + EMPTY1;
            }
        };
    }

    static IDescriptor createExit(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            protected String deepVision() {
                return EMPTY1 + EXIT + EMPTY1;
            }
            protected String troubleVision() {
                return deepVision();
            }
        };
    }

    static IDescriptor createMagic(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            protected String deepVision() {
                return EMPTY1 + MAGIC + EMPTY1;
            }
            protected String troubleVision() {
                return deepVision();
            }
        };
    }

    static IDescriptor createMonster(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            protected String deepVision() {
                int n = ((IMonsterRoom) room()).monstersNb();
                String res = (n <= 9)
                        ? String.valueOf(n)
                        : String.valueOf((char) ('a' + (n - 10)));
                return EMPTY1 + res + EMPTY1;
            }
        };
    }

    static IDescriptor createNormal(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            protected String deepVision() {
                return isVisited() ? VISITED3 : EMPTY3;
            }
        };
    }

    // TYPES IMBRIQUES

    private abstract static class AbstractDescriptor implements IDescriptor {
        private final MazeRoom room;
        private final int row;
        private final int col;
        private final Maze maze;

        private AbstractDescriptor(MazeRoom r, Maze m, int i, int j) {
            room = r;
            row = i;
            col = j;
            maze = m;
        }
        public String describe(boolean withLight) {
            IDescriptor p = room.getPlayerDesc();
            if (p != null) {
                return EMPTY1 + p.describe(withLight) + EMPTY1;
            } else {
                if (withLight || supermanIsNear()) {
                    return deepVision();
                } else {
                    return troubleVision();
                }
            }
        }
        protected abstract String deepVision();
        protected String troubleVision() {
            return isVisited() ? deepVision() : EMPTY3;
        }
        protected boolean supermanIsNear() {
            return maze.powerPlayerAround(row, col);
        }
        protected IRoom room() {
            return room.getRoom();
        }
        protected boolean isVisited() {
            return room.isVisited();
        }
    }
}
