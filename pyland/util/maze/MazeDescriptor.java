package pyland.util.maze;

class MazeDescriptor implements IDescriptor {

    // ATTRIBUTS STATIQUES

    private static final String NL = "\n";

    // ATTRIBUTS

    private final Maze maze;
    private final IDescriptor[][] walls;
    private final IDescriptor[][] floors;
    private final IDescriptor[][] corners;

    // CONSTRUCTEURS

    MazeDescriptor(Maze m) {
        maze = m;
        walls = new IDescriptor[maze.rowsNb()][];
        floors = new IDescriptor[maze.rowsNb() + 1][];
        corners = new IDescriptor[maze.rowsNb() + 1][];
        initDescriptors();
    }

    // REQUETES

    public String describe(boolean light) {
        String s = describeFloorLine(0, true) + NL;
        for (int i = 0; i < maze.rowsNb() - 1; i++) {
            s += describeRoomLine(i, light) + NL;
            s += describeFloorLine(i + 1, light) + NL;
        }
        s += describeRoomLine(maze.rowsNb() - 1, light) + NL;
        s += describeFloorLine(maze.rowsNb(), true) + NL;
        return s;
    }

    // OUTILS

    private void initDescriptors() {
        for (int r = 0; r < maze.rowsNb() + 1; r++) {
            if (r < maze.rowsNb()) {
                walls[r] = new IDescriptor[maze.colsNb() + 1];
            }
            floors[r] = new IDescriptor[maze.colsNb()];
            corners[r] = new IDescriptor[maze.colsNb() + 1];
            for (int c = 0; c < maze.colsNb() + 1; c++) {
                if (r < maze.rowsNb()) {
                    walls[r][c] = createWD(r, c);
                }
                if (c < maze.colsNb()) {
                    floors[r][c] = createFD(r, c);
                }
                corners[r][c] = createCD(r, c);
            }
        }
    }

    private IDescriptor createWD(int r, int c) {
        MazeRoom e = (c == maze.colsNb()) ? null : maze.get(r, c);
        MazeRoom w = (c == 0) ? null : maze.get(r, c - 1);
        return BorderFactory.createWall(e, w);
    }

    private IDescriptor createCD(int r, int c) {
        MazeRoom ne = (r == 0 || c == maze.colsNb())
                ? null : maze.get(r - 1, c);
        MazeRoom se = (r == maze.rowsNb() || c == maze.colsNb())
                ? null : maze.get(r, c);
        MazeRoom sw = (r == maze.rowsNb() || c == 0)
                ? null : maze.get(r, c - 1);
        MazeRoom nw = (r == 0 || c == 0)
                ? null : maze.get(r - 1, c - 1);
        return BorderFactory.createCorner(ne, se, sw, nw);
    }

    private IDescriptor createFD(int r, int c) {
        if ((r == 0 && c == maze.colsNb() - 1)
                || (r == maze.rowsNb() && c == 0)) {
            return BorderFactory.createCrossing();
        }
        MazeRoom n = (r == 0) ? null : maze.get(r - 1, c);
        MazeRoom s = (r == maze.rowsNb()) ? null : maze.get(r, c);
        return BorderFactory.createFloor(n, s);
    }

    /**
     * Dessine la ieme ligne de pièces.
     */
    private String describeRoomLine(int i, boolean light) {
        String s = walls[i][0].describe(true);
        for (int j = 0; j < maze.colsNb() - 1; j++) {
            s += maze.get(i, j).getDesc().describe(light);
            s += walls[i][j + 1].describe(light);
        }
        s += maze.get(i, maze.colsNb() - 1).getDesc().describe(light);
        s += walls[i][maze.colsNb()].describe(true);
        return s;
    }

    /**
     * Dessine le ieme trait horizontal de la grille.
     */
    private String describeFloorLine(int i, boolean light) {
        String s = corners[i][0].describe(true);
        for (int j = 0; j < maze.colsNb() - 1; j++) {
            s += floors[i][j].describe(light);
            s += corners[i][j + 1].describe(light);
        }
        s += floors[i][maze.colsNb() - 1].describe(light);
        s += corners[i][maze.colsNb()].describe(true);
        return s;
    }
}
